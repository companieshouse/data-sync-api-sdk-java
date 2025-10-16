package uk.gov.companieshouse.api.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.metrics.request.PrivateCompanyMetricsGet;
import uk.gov.companieshouse.api.metrics.MetricsApi;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.logging.Logger;

import java.util.Optional;


@Service
public class CompanyMetricsApiService {

    private static final String GET_COMPANY_METRICS_ENDPOINT = "/company/%s/metrics";

    private final InternalApiClient internalApiClient;

    private final Logger logger;

    @Value("${chs.api.metrics.url}")
    private String basePath;

    @Autowired
    public CompanyMetricsApiService(Logger logger, InternalApiClient internalApiClient) {
        super();
        this.logger = logger;
        this.internalApiClient = internalApiClient;
    }

    /**
     * Get company metrics.
     *
     * @param companyNumber company number.
     * @return company metrics.
     */
    public Optional<MetricsApi> getCompanyMetrics(final String companyNumber) {
        logger.trace(String.format("Started : getCompanyMetrics for Company Number %s ",
                companyNumber
        ));
        internalApiClient.setBasePath(basePath);
        logger.trace(String.format("Created client %s", internalApiClient.getBasePath()));
        PrivateCompanyMetricsGet companyMetrics =
                internalApiClient.privateCompanyMetricsResourceHandler()
                        .getCompanyMetrics(
                                String.format(GET_COMPANY_METRICS_ENDPOINT, companyNumber));
        try {
            ApiResponse<MetricsApi> execute = companyMetrics.execute();
            return Optional.ofNullable(execute.getData());
        } catch (URIValidationException exp) {
            logger.error("Error occurred while calling getCompanyMetrics endpoint.");
        } catch (ApiErrorResponseException exp) {
            if (exp.getStatusCode() == 404) {
                logger.error(String.format(
                        "Error occurred while calling getCompanyMetrics endpoint. "
                                + "Status Code: 404 - NOT FOUND for %s.", companyNumber));
            } else {
                logger.error(String.format("Error calling getCompanyMetrics endpoint. "
                                + "Status Code: [%s] - message [%s]", exp.getStatusCode(),
                        exp.getStatusMessage()));
                throw new ResponseStatusException(resolveHttpStatus(exp.getStatusCode()),
                        exp.getStatusMessage(), exp);
            }
        } catch (Exception exp) {
            logger.error(String.format("Error calling getCompanyMetrics endpoint. "
                            + " message [%s]", exp.getMessage()));
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
