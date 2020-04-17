(ns clj-test.service.patient-service
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clj-test.model.patient :as patient]
            [clj-test.config.db :as database]
            [clj-test.repository.patient-repository :as patient-repo]))

(defn get-all
  "Gets all patinets from the database"
  []
  (patient-repo/get-all))

