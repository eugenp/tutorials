package com.baeldung.j2cl.taskmanager;

import elemental2.core.JsArray;
import elemental2.dom.*;
import elemental2.promise.Promise;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType
public class MyJ2CLApp {

    private static final String API_URL = "https://api.restful-api.dev/objects";

    /**
     * If this is null, it means there was no 'uuid' in the URL,
     * and we have not yet created an object on the server.
     */
    private static String uuid = getUUIDFromURL();

    // We'll store all tasks in a single array in memory
    private static JsArray<String> tasksArray = new JsArray<>();

    private static final HTMLInputElement taskInput = (HTMLInputElement) DomGlobal.document.getElementById("taskInput");
    private static final HTMLButtonElement addTaskButton = (HTMLButtonElement) DomGlobal.document.getElementById("addTask");
    private static final HTMLUListElement taskList = (HTMLUListElement) DomGlobal.document.getElementById("taskList");

    public void onModuleLoad() {
        // Only fetch existing tasks if we already have a uuid in the URL
        if (uuid != null) {
            fetchTasks();
        }

        // Wire up the "Add Task" button
        addTaskButton.addEventListener("click", event -> {
            String taskText = taskInput.value.trim();
            if (!taskText.isEmpty()) {
                sendTask(taskText);
                taskInput.value = "";
            }
        });
    }

    /**
     * Fetch the tasks array from the server (GET /objects/<uuid>)
     * and display them in the UI.
     */
    private void fetchTasks() {
        DomGlobal.fetch(API_URL + "/" + uuid)
            .then(response -> {
                if (!response.ok) {
                    // If 404, it just means there's no object yet
                    if (response.status == 404) {
                        DomGlobal.console.log("No existing tasks for ID:", uuid);
                        return null; // no tasks
                    }
                    throw new Error("HTTP error " + response.status);
                }
                return response.json();
            })
            .then(data -> {
                if (data != null) {
                    // data should contain "id", "name", and "data"
                    JsPropertyMap<Object> map = (JsPropertyMap<Object>) data;
                    Object storedData = map.get("data");
                    if (storedData != null) {
                        // We expect an array of strings, so cast accordingly
                        JsArray<Object> arr = (JsArray<Object>) storedData;
                        for (int i = 0; i < arr.length; i++) {
                            String taskText = arr.getAt(i)
                                .toString();
                            tasksArray.push(taskText);
                            addTaskToUI(taskText);
                        }
                    }
                }
                return null;
            })
            .catch_(error -> {
                DomGlobal.console.error("Failed to fetch tasks:", error);
                showErrorMessage("Could not fetch tasks. Check console.");
                return null;
            });
    }

    /**
     * Adds a new task in our in-memory list, then decides:
     *  - If uuid == null, POST to the server to create a new object
     *    and store the returned id in 'uuid'.
     *  - Else, PUT the updated tasks array to the server.
     */
    private void sendTask(String taskText) {
        if (taskText == null || taskText.isEmpty()) {
            throw new IllegalArgumentException("Task text cannot be empty");
        }

        addTaskButton.disabled = true; // disable to prevent rapid clicks

        // Add the new task locally
        tasksArray.push(taskText);

        // If we don't have an ID yet, create the object first
        if (uuid == null) {
            createObjectOnServer().then(ignore -> {
                // Now we have a uuid, let's do a PUT to keep it consistent
                return updateTasksOnServer();
            })
                .then(ignore -> {
                    // All good, add to UI
                    addTaskToUI(taskText);
                    return null;
                })
                .catch_(error -> {
                    // Revert local addition if something failed
                    tasksArray.splice(tasksArray.length - 1, 1);
                    DomGlobal.console.error("Error creating object:", error);
                    showErrorMessage("Failed to create tasks object. See console.");
                    return null;
                })
                .then(v -> {
                    addTaskButton.disabled = false;
                    return null;
                });
        } else {
            // We already have a uuid, so just PUT the entire array
            updateTasksOnServer().then(ignore -> {
                addTaskToUI(taskText);
                return null;
            })
                .catch_(error -> {
                    // Revert local addition if server call fails
                    tasksArray.splice(tasksArray.length - 1, 1);
                    DomGlobal.console.error("Error updating tasks:", error);
                    showErrorMessage("Failed to save task. Please see the console.");
                    return null;
                })
                .then(v -> {
                    addTaskButton.disabled = false;
                    return null;
                });
        }
    }

    /**
     * POST a new object (with no ID) so the server auto-generates it.
     * We store the returned "id" in 'uuid' and rewrite the URL.
     */
    private Promise<Object> createObjectOnServer() {
        // We create a brand-new object, letting the server assign "id".
        // We'll use "data" = tasksArray so we don't lose the first insertion.
        JsPropertyMap<Object> jsonBody = JsPropertyMap.of();
        jsonBody.set("name", "MyTasks"); // Arbitrary "name"
        jsonBody.set("data", tasksArray);
        String jsonData = jsonStringify(jsonBody);

        RequestInit requestInit = RequestInit.create();
        requestInit.setMethod("POST");
        requestInit.setBody(jsonData);

        Headers headers = new Headers();
        headers.append("Content-Type", "application/json");
        requestInit.setHeaders(headers);

        return DomGlobal.fetch(API_URL, requestInit)
            .then(response -> {
                if (!response.ok) {
                    // read body for debugging
                    return response.text()
                        .then(bodyText -> {
                            throw new Error("POST error " + response.status + ": " + bodyText);
                        });
                }
                // otherwise parse the JSON
                return response.json();
            })
            .then(result -> {
                // result should have an "id" property
                JsPropertyMap<Object> map = (JsPropertyMap<Object>) result;
                Object idObj = map.get("id");
                if (idObj == null) {
                    throw new Error("No 'id' returned in creation response!");
                }
                uuid = idObj.toString(); // store it
                DomGlobal.console.log("New ID created:", uuid);

                // rewrite the browser URL so we have ?uuid=xxx
                rewriteURLwithUUID(uuid);
                return null;
            });
    }

    /**
     * Once we have a uuid, we PUT the entire tasks array to update the server object.
     * {
     *   "id":   <the server-assigned ID>,
     *   "name": (some name, optional),
     *   "data": [ ... all tasks ... ]
     * }
     */
    private Promise<Object> updateTasksOnServer() {
        if (uuid == null) {
            // Should never happen once we have created the object
            return Promise.reject("No UUID available to update!");
        }

        JsPropertyMap<Object> jsonBody = JsPropertyMap.of();
        jsonBody.set("id", uuid);         // must match the server's assigned ID
        jsonBody.set("name", "MyTasks");  // optional, can keep it consistent
        jsonBody.set("data", tasksArray);
        String jsonData = jsonStringify(jsonBody);

        RequestInit requestInit = RequestInit.create();
        requestInit.setMethod("PUT");
        requestInit.setBody(jsonData);

        Headers headers = new Headers();
        headers.append("Content-Type", "application/json");
        requestInit.setHeaders(headers);

        return DomGlobal.fetch(API_URL + "/" + uuid, requestInit)
            .then(response -> {
                return response.text()
                    .then(bodyText -> {
                        DomGlobal.console.log("Response from PUT:", bodyText);
                        if (!response.ok) {
                            throw new Error("HTTP " + response.status + ": " + bodyText);
                        }
                        return null; // success
                    });
            });
    }

    /**
     * When we add a task to the UI, attach a Delete button
     * that also removes the item from the server.
     */
    private void addTaskToUI(String taskText) {
        HTMLLIElement taskItem = (HTMLLIElement) DomGlobal.document.createElement("li");
        taskItem.textContent = taskText;

        HTMLButtonElement deleteButton = (HTMLButtonElement) DomGlobal.document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.classList.add("deleteButton");

        // On delete, remove from tasksArray, update server, then remove from UI
        deleteButton.addEventListener("click", e -> {
            // Find index of this task in the array
            for (int i = 0; i < tasksArray.length; i++) {
                if (tasksArray.getAt(i)
                    .equals(taskText)) {
                    tasksArray.splice(i, 1); // remove 1 item at index i
                    break;
                }
            }

            updateTasksOnServer().then(ignore -> {
                // If success, remove from UI
                taskItem.remove();
                return null;
            })
                .catch_(error -> {
                    // Revert local removal if failed
                    tasksArray.push(taskText);
                    DomGlobal.console.error("Error deleting task:", error);
                    showErrorMessage("Failed to delete task on server.");
                    return null;
                });
        });

        taskItem.appendChild(deleteButton);
        taskList.appendChild(taskItem);
    }

    private void showErrorMessage(String message) {
        HTMLDivElement errorDiv = (HTMLDivElement) DomGlobal.document.createElement("div");
        errorDiv.textContent = message;
        errorDiv.classList.add("errorMessage");
        addTaskButton.parentNode.insertBefore(errorDiv, taskList);

        DomGlobal.setTimeout((e) -> errorDiv.remove(), 5000);
    }

    /**
     * Try to read the existing 'uuid' from the URL (e.g. ?uuid=12345).
     * Return null if not present.
     */
    private static String getUUIDFromURL() {
        URLSearchParams params = new URLSearchParams(DomGlobal.window.location.search);
        if (params.has("uuid")) {
            return params.get("uuid");
        }
        return null;
    }

    /**
     * Once we know the server-assigned uuid, we can rewrite the URL to include it.
     */
    private void rewriteURLwithUUID(String newUUID) {
        String url = DomGlobal.window.location.href;
        URLSearchParams params = new URLSearchParams(DomGlobal.window.location.search);
        params.set("uuid", newUUID);

        // If original had ?... we replace; otherwise append ?uuid=...
        String baseUrl = url.contains("?") ? url.substring(0, url.indexOf("?")) : url;
        String newUrl = baseUrl + "?" + params.toString();

        DomGlobal.window.history.replaceState(null, "", newUrl);
    }

    @JsMethod(namespace = JsPackage.GLOBAL, name = "JSON.stringify")
    private static native String jsonStringify(Object obj);
}
