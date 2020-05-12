(ns clj-test.service.patient-service
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clj-test.model.patient :as patient]
            [clj-test.config.db :as database]
            [clj-test.repository.patient-repository :as patient-repo]
            [clj-test.specification.query.single :as single-spec]
            [clj-test.specification.query.list :as list-spec]
            [clj-test.specification.query.modify :as modify-spec]))

(defn get-list
  "Gets list patinets from the database"
  [offset limit]
  (patient-repo/get-list #(list-spec/get-sort-asc-list-query % offset limit)))

(defn get-by-id
  "Gets only one patient using its id"
  [id]
  (patient-repo/get-single #(single-spec/get-by-id % id)))

(defn get-total
  "Gets total patients in database"
  []
  (:count (patient-repo/get-single #(single-spec/get-count %))))

(defn patient-create
  "Adds a new patient to the database, returns the id if ok, null if invalid"
  [patient]
  (patient-repo/patient-create #(modify-spec/insert-single-query % patient-repo/patient-query-fields (patient-repo/prepare-patient patient))))

(defn patient-update
  "Updates an existing patient on the database"
  [id data]
  (patient-repo/patient-update #(modify-spec/update-by-id-query % patient-repo/patient-query-fields id (merge {:id id} (patient-repo/prepare-patient data)))))

(defn patient-delete
  "Deletes an existing patient on the database, returns id of deleted patient"
  [id]
  (patient-repo/patient-delete #(modify-spec/delete-query % id)))

