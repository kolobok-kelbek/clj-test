(ns clj-test.config.db
  (:use [korma.db]
        [clojure.java.shell :only [sh]]))

(defdb db (postgres {:host "db"
                     :port 5432
                     :db "dev"
                     :user "dev"
                     :password "dev"}))

(defn reinit
  "reinitialize database"
  []
  (sh "lein" "flyway" "clean")
  (sh "lein" "flyway" "migrate"))
