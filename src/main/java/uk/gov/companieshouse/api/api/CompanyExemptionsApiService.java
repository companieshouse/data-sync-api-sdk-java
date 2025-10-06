package uk.gov.companieshouse.api.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.exemptions.CompanyExemptions;
import uk.gov.companieshouse.api.handler.delta.exemptions.request.PrivateCompanyExemptionsGetAll;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.logging.Logger;

import java.util.Optional;

@Service
public class CompanyExemptionsApiService {

    private static final String GET_COMPANY_EXEMPTIONS_ENDPOINT = "/company/%s/exemptions";

    @Autowired
    InternalApiClient internalApiClient;

    @Value("${chs.api.exemptions.url}")
    private String basePath;

    @Autowired
    Logger logger;

    public Optional<CompanyExemptions> getCompanyExemptions (final String companyNumber) {
        logger.trace(String.format("Started : getCompanyExemptions for Company Number %s ", companyNumber));
        internalApiClient.setBasePath(basePath);
        logger.trace(String.format("Created client %s", internalApiClient.getBasePath()));
        PrivateCompanyExemptionsGetAll companyExemptions = 
                internalApiClient.privateDeltaResourceHandler()
                        .getCompanyExemptionsResource(
                                String.format(GET_COMPANY_EXEMPTIONS_ENDPOINT, companyNumber));

        try {
            ApiResponse<CompanyExemptions> execute = companyExemptions.execute();
            return Optional.ofNullable(execute.getData());
        } catch (URIValidationException e) {
            logger.error("Error occurred while calling getCompanyExemptions endpoint.");
        } catch (ApiErrorResponseException e) {
            if (e.getStatusCode() == 404) {
                logger.error(String.format(
                        "Error occurred while calling getCompanyExemptions endpoint. " 
                                + " Status Code: 404 - NOT FOUND for %s", companyNumber));
            } else {
                logger.error(String.format(
                    "Error calling getCompanyExemptions endpoint. " 
                        + "Status Code: [%s] - message [%s]", e.getStatusCode(), e.getStatusMessage()));
                throw new ResponseStatusException(resolveHttpStatus(e.getStatusCode()), e.getStatusMessage(), e);
            }
        } catch (Exception e) {
            logger.error(String.format(
                    "Error calling getCompanyExemptions endpoint. " 
                        + " message [%s]", e.getMessage()));
        }
        return Optional.empty();
    }

    /**
     Changes a number status code into HttpStatus to prevent errors with unknown codes.
     This is needed because Spring Boot 3.x is stricter than version 2.7.x.
     In 3.x, using just a number can cause problems, so we use HttpStatus instead.
     */
    private org.springframework.http.HttpStatus resolveHttpStatus(int statusCode) {
        org.springframework.http.HttpStatus status = org.springframework.http.HttpStatus.resolve(statusCode);
        return (status != null) ? status : org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
