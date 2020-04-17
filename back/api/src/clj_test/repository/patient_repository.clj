(ns clj-test.repository.patient-repository
  (:use [korma.core :only [select insert values delete where set-fields]])
  (:require [clj-test.model.patient :as patient-model]))

(defn get-all
  "Gets all patients from the database"
  []
  (select patient-model/patient))

