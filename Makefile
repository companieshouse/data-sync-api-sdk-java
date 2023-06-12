.PHONY: all
all: build

.PHONY: clean
clean:
	mvn clean

.PHONY: build
build:
	mvn compile

.PHONY: test
test: test-unit

.PHONY: test-unit
test-unit:
	mvn test

.PHONY: package
package:
ifndef version
	$(error No version given. Aborting)
endif
	$(info Packaging version: $(version))
	mvn versions:set -DnewVersion=$(version) -DgenerateBackupPoms=false
	mvn package -DskipTests=true

.PHONY: dist
dist: clean package

.PHONY: publish
publish:
	mvn jar:jar deploy:deploy

.PHONY: sonar
sonar:
	mvn sonar:sonar

.PHONY: sonar-pr-analysis
sonar-pr-analysis:
	mvn sonar:sonar -P sonar-pr-analysis

FAIL_BUILD_CVSS_LIMIT ?= 5

.PHONY: security-check
security-check: security-report
	mvn org.owasp:dependency-check-maven:check -DassemblyAnalyzerEnabled=false -DfailBuildOnCVSS=$(FAIL_BUILD_CVSS_LIMIT)

.PHONY: security-report
security-report:
	mvn org.owasp:dependency-check-maven:check -DassemblyAnalyzerEnabled=false
	mvn sonar:sonar