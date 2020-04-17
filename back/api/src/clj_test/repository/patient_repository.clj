(ns clj-test.repository.patient-repository
  (:use [korma.core :only [select insert values delete where set-fields]])
  (:require [clojure.tools.logging :as log]
            [clj-test.model.patient :as patient-model]))

(defn get-all
  "Gets all patients from the database"
  []
  (select patient-model/patient))

(defn get-by-id
  "Gets only one patient using its id"
  [id]
  (log/info (str "get-by-id repository, id: " id))
  (select patient-model/patient (where {:id id})))

