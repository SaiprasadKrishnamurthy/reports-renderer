package org.sai.raas.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sai.raas.model.ReportsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scratchpad.Scratchpad;

/**
 * Created by saipkri on 05/07/16.
 */
@RestController
public class RaasApi {

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK - All good to go - a new signed JSon Web Token is returned as a text"),
                    @ApiResponse(code = 401, message = "Authentication failure")
            }
    )
    @ApiOperation(value = "Simple hello world endpoint")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/plain")
    public String index() {
        return "Hello World!";
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 401, message = "Authentication failure")
            }
    )
    @ApiOperation(value = "Gets a base64 encoded image of a pie chart given the series data")
    @RequestMapping(value = "/image/{reportId}", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> image(@PathVariable("reportId") final String reportId, @RequestBody final ReportsRequest reportsRequest) throws Exception {
        return new ResponseEntity(Scratchpad.base64Image(reportsRequest), HttpStatus.OK);
    }
}
