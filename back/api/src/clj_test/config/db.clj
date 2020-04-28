(ns clj-test.config.db
  (:use [clojure.java.shell :only [sh]]))

(def db-spec
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "//db:5432/dev"
   :user "dev"
   :password "dev"})

(defn reinit
  "reinitialize database"
  []
  (sh "lein" "flyway" "clean")
  (sh "lein" "flyway" "migrate"))
