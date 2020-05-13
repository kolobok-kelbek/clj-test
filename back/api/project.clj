(defproject clj-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.clojure/data.generators "1.0.0"]                 
                 [org.clojure/java.jdbc "0.7.1"]
                 [compojure "1.6.1"]
                 [metosin/compojure-api "1.1.11"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.3.2"]
                 [cheshire "5.10.0"]
                 [honeysql "0.9.10"]
                 [nilenso/honeysql-postgres "0.2.6"]
                 [org.flywaydb/flyway-core "6.4.0"]
                 [org.postgresql/postgresql "42.2.4"]]

  :plugins [[lein-ring "0.12.5"]
            [lein-shell "0.4.2"]
            [lein-exec "0.3.4"]]

  :ring {:handler clj-test.handler/app}
  
  :profiles { :test {:resource-paths ["resources/test"]
                     :dependencies [[faker "0.2.2"]
                                    [re-rand "0.1.0"]]}
              :dev
                    {:resource-paths ["resources/dev"]
                     :dependencies [[javax.servlet/servlet-api "2.5"]
                                    [ring/ring-mock "0.3.1"]]
                    }
            }

  :eastwood {:exclude-linters [:constant-test]
             :include-linters [:deprecations]
             :exclude-namespaces [clojure-rest.config.routes]
             }
  :aliases {
              "db-clean"   ["exec" "-ep" "(use 'clj-test.migration) (clean)"]
              "db-migrate" ["exec" "-ep" "(use 'clj-test.migration) (migrate)"]
              "db-info"    ["exec" "-ep" "(use 'clj-test.migration) (info)"]
              "db-reset"   ["exec" "-ep" "(use 'clj-test.migration) (reset)"]
           })

