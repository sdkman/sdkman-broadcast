package net.gvmtool

import groovy.json.JsonSlurper
import wslite.rest.RESTClient

import static cucumber.api.groovy.Hooks.After
import static db.MongoHelper.*

def BASE_URL = "http://localhost:8080"
restClient = new RESTClient(BASE_URL)
slurper = new JsonSlurper()

lineOrder = ["first": 0, "second": 1, "third": 2, "forth": 3, "fifth": 4]
statusCodes = [200: "OK", 403: "FORBIDDEN", 404: "NOT_FOUND"]

if(!binding.hasVariable("db")) {
    db = prepareDB()
}

After() {
    dropCollectionFromDb(db, "application")
    dropCollectionFromDb(db, "broadcast")
    dropCollectionFromDb(db, "candidates")
}