/*******************************************************************************
* Copyright (c) 2018, 2019 IBM Corporation and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*     IBM Corporation - initial API and implementation
*******************************************************************************/
function displayMetrics() {
    getSystemMetrics();
}

function getSystemMetrics() {
    var url = "http://localhost:9080/metrics";
    var req = new XMLHttpRequest();

    var metricToDisplay = {};
    metricToDisplay["application_getProperties_total"] = "Request Count";
    metricToDisplay["application_io_openliberty_sample_system_SystemResource_getPropertiesTime_one_min_rate_per_second"] = "Min Request Time (ms)";
    metricToDisplay["application_io_openliberty_sample_system_SystemResource_getPropertiesTime_mean_seconds"] = "Mean Request Time (ms)";
    metricToDisplay["application_io_openliberty_sample_system_SystemResource_getPropertiesTime_max_seconds"] = "Max Request Time (ms)";
    metricToDisplay["base_cpu_processCpuLoad_percent"] = "System CPU Usage (%)";
    metricToDisplay["base_memory_usedHeap_bytes"] = "System Heap Usage (MB)";

    var metricToMatch = "^(";
    for (var metricKey in metricToDisplay) {
        metricToMatch += metricKey + "|"
    }
    // remove the last |
    metricToMatch = metricToMatch.substring(0, metricToMatch.length-1);
    metricToMatch += ")\\s*(\\S*)$"

    req.onreadystatechange = function() {
        if (req.readyState != 4) return; // Not there yet
        if (req.status != 200) {
            document.getElementById("metricsText").innerHTML = req.statusText;
            return;
        }

        var resp = req.responseText;
        var regexpToMatch = new RegExp(metricToMatch, "gm");
        var matchMetrics = resp.match(regexpToMatch);

        var keyValPairs = {};
        for (var metricKey in metricToDisplay) {
            matchMetrics.forEach(function(line) {
                var keyToMatch = metricKey + " (.*)";
                var keyVal = line.match(new RegExp(keyToMatch));
                if (keyVal) {
                    var val = keyVal[1];
                    if (metricKey.indexOf("application:io_openliberty_sample_system_system_resource_get_properties_time") === 0) {
                        val = val * 1000;
                    } else if (metricKey.indexOf("base:memory_used_heap_bytes") === 0) {
                        val = val / 1000000;
                    }
                    keyValPairs[metricToDisplay[metricKey]] = val;
                }
            })
        }

        var table = document.getElementById("metricsTableBody");
        for (key in keyValPairs) {
            var row = document.createElement("tr");
            var keyData = document.createElement("td");
            keyData.innerText = key;
            var valueData = document.createElement("td");
            valueData.innerText = keyValPairs[key];
            row.appendChild(keyData);
            row.appendChild(valueData);
            table.appendChild(row);
        }

        addSourceRow(table, url);
    };

    req.open("GET", url, true);
    req.send();
}

function displaySystemProperties() {
    getSystemPropertiesRequest();
}

function getSystemPropertiesRequest() {
    var propToDisplay = ["java.vendor", "java.version", "user.name", "os.name", "wlp.install.dir", "wlp.server.name" ];
    var url = "http://localhost:9080/system/properties";
    var req = new XMLHttpRequest();
    var table = document.getElementById("systemPropertiesTable");
    // Create the callback:
    req.onreadystatechange = function () {
        if (req.readyState != 4) return; // Not there yet
        displayMetrics();
        if (req.status != 200) {
            table.innerHTML = "";
            var row = document.createElement("tr");
            var th = document.createElement("th");
            th.innerText = req.statusText;
            row.appendChild(th);
            table.appendChild(row);

            addSourceRow(table, url);
            return;
        }
        // Request successful, read the response
        var resp = JSON.parse(req.responseText);
        for (var i = 0; i < propToDisplay.length; i++) {
            var key = propToDisplay[i];
            if (resp.hasOwnProperty(key)) {
                var row = document.createElement("tr");
                var keyData = document.createElement("td");
                keyData.innerText = key;
                var valueData = document.createElement("td");
                valueData.innerText = resp[key];
                row.appendChild(keyData);
                row.appendChild(valueData);
                table.appendChild(row);
            }
        }

        addSourceRow(table, url);
    };
    req.open("GET", url, true);
    req.send();
}

function displayHealth() {
    getHealth();
}

function getHealth() {
    var url = "http://localhost:9080/health";
    var req = new XMLHttpRequest();

    var healthBox = document.getElementById("healthBox");
    var serviceName = document.getElementById("serviceName");
    var healthStatus = document.getElementById("serviceStatus");
    var healthIcon = document.getElementById("healthStatusIconImage");

    req.onreadystatechange = function () {
        if (req.readyState != 4) return; // Not there yet

        // Request successful, read the response
        if (req.responseText) {
            var resp = JSON.parse(req.responseText);
            var service = resp.checks[0]; //TODO: use for loop for multiple services

            resp.checks.forEach(function (service) {
                serviceName.innerText = service.name;
                healthStatus.innerText = service.status;

                if (service.status === "UP") {
                    healthBox.style.backgroundColor = "#f0f7e1";
                    healthIcon.setAttribute("src", "img/systemUp.svg");
                } else {
                    healthBox.style.backgroundColor = "#fef7f2";
                    healthIcon.setAttribute("src", "img/systemDown.svg");
                }
            });
        }
        var table = document.getElementById("healthTable");

        addSourceRow(table, url);
    };
    req.open("GET", url, true);
    req.send();
}

function displayConfigProperties() {
    getConfigPropertiesRequest();
}

function getConfigPropertiesRequest() {
    var url = "http://localhost:9080/config";
    var req = new XMLHttpRequest();

    var configToDisplay = {};
    configToDisplay["io_openliberty_sample_system_inMaintenance"] = "System In Maintenance";
    configToDisplay["io_openliberty_sample_testConfigOverwrite"] = "Test Config Overwrite";
    configToDisplay["io_openliberty_sample_port_number"] = "Port Number";
    // Create the callback:
    req.onreadystatechange = function () {
        if (req.readyState != 4) return; // Not there yet
        if (req.status != 200) {
            return;
        }

        // Request successful, read the response
        var resp = JSON.parse(req.responseText);
        var configProps = resp["ConfigProperties"];
        var table = document.getElementById("configTableBody");
        for (key in configProps) {
            var row = document.createElement("tr");
            var keyData = document.createElement("td");
            keyData.innerText = configToDisplay[key];
            var valueData = document.createElement("td");
            valueData.innerText = configProps[key];
            row.appendChild(keyData);
            row.appendChild(valueData);
            table.appendChild(row);
        }

        addSourceRow(table, url);
    }
    req.open("GET", url, true);
    req.send();
}

function toggle(e) {
    var callerElement;
    if (!e) {
        if (window.event) {
            e = window.event;
            callerElement = e.currentTarget;
        } else {
            callerElement = window.toggle.caller.arguments[0].currentTarget; // for firefox
        }
    }

    var classes = callerElement.parentElement.classList;
    var collapsed = classes.contains("collapsed");
    var caretImg = callerElement.getElementsByClassName("caret")[0];
    var caretImgSrc = caretImg.getAttribute("src");
    if (collapsed) { // expand the section
        classes.replace("collapsed", "expanded");
        caretImg.setAttribute("src", caretImgSrc.replace("down", "up"));
    } else { // collapse the section
        classes.replace("expanded", "collapsed");
        caretImg.setAttribute("src", caretImgSrc.replace("up", "down"));
    }
}

function addSourceRow(table, url) {
    var sourceRow = document.createElement("tr");
    sourceRow.classList.add("sourceRow");
    var sourceText = document.createElement("td");
    sourceText.setAttribute("colspan", "100%");
    sourceText.innerHTML = "API Source\: <a href='"+url+"'>"+url+"</a>";
    sourceRow.appendChild(sourceText);
    table.appendChild(sourceRow);
}
