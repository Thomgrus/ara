package com.decathlon.ara.scenario.cucumber.upload;

import com.decathlon.ara.Entities;
import com.decathlon.ara.domain.enumeration.Technology;
import com.decathlon.ara.scenario.cucumber.bean.Feature;
import com.decathlon.ara.scenario.cucumber.util.CucumberReportUtil;
import com.decathlon.ara.scenario.cucumber.util.ScenarioExtractorUtil;
import com.decathlon.ara.service.exception.BadRequestException;
import com.decathlon.ara.scenario.common.upload.ScenarioUploader;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CucumberScenarioUploader {

    @NonNull
    private final ScenarioUploader uploader;

    /**
     * Upload the Cucumber scenario set of a test type.
     *
     * @param projectId  the ID of the project in which to work
     * @param sourceCode the source-code determining the location of the files that are uploaded
     * @param json       the report.json file as generated by a cucumber --dry-run
     * @throws BadRequestException if the source cannot be found, the source code is not using CUCUMBER technology, or something goes wrong while parsing the report content
     */
    public void uploadCucumber(long projectId, String sourceCode, String json) throws BadRequestException {
        uploader.processUploadedContent(projectId, sourceCode, Technology.CUCUMBER, source -> {
            // Extract and save scenarios of the source from the report.json
            List<Feature> features;
            try {
                features = CucumberReportUtil.parseReportJson(json);
            } catch (IOException e) {
                log.error("Cannot parse uploaded Cucumber report.json", e);
                throw new BadRequestException("Cannot parse uploaded Cucumber report.json", Entities.SCENARIO, "cannot_parse_report_json");
            }
            return ScenarioExtractorUtil.extractScenarios(source, features);
        });
    }
}
