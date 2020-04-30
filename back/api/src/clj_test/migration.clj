(ns clj-test.migration
  (:import org.flywaydb.core.Flyway
           org.flywaydb.core.internal.info.MigrationInfoDumper)
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def db-spec
  (->> "db.edn" io/resource slurp edn/read-string))

(def db-url 
  (str "jdbc:" 
        (get db-spec :subprotocol)
        ":"
        (get db-spec :subname)))

;; Initialize Flyway object
(def flyway
  (.. (Flyway/configure)
      (dataSource db-url (get db-spec :user) (get db-spec :password))
      (locations (into-array String (get (get db-spec :flyway) :locations)))
      load))

(defn migrate
  []
  (.migrate flyway))

(defn clean
  []
  (.clean flyway))

(defn reset
  []
  (clean) (migrate))

(defn info []
  (println (MigrationInfoDumper/dumpToAsciiTable (.all (.info flyway)))))

