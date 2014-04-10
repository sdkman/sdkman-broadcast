package net.gvmtool

import wslite.rest.RESTClient

import static cucumber.api.groovy.Hooks.After
import static db.MongoHelper.*

def BASE_URL = "http://localhost:8080"
restClient = new RESTClient(BASE_URL)

if(!binding.hasVariable("db")) {
    db = prepareDB()
}

After() {
    dropCollectionFromDb(db, "application")
    dropCollectionFromDb(db, "broadcast")
    dropCollectionFromDb(db, "candidates")
}