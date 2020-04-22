(defproject clj-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.logging "0.4.0"]
                 [compojure "1.6.1"]
                 [http-kit "2.2.0"]
                 [metosin/compojure-api "1.1.11"]
                 [ring/ring-json "0.4.0"] ; ring server json support
                 [ring/ring-defaults "0.3.1"] ; ring server default dependencies
                 [org.clojure/data.json "0.2.6"]
                 [ring/ring-defaults "0.3.2"]
                 [clojure.java-time "0.3.2"]
                 [org.clojure/java.jdbc "0.7.1"]
                 [korma "0.4.3"]
                 [org.postgresql/postgresql "42.2.4"]]

  :plugins [[lein-ring "0.12.5"]
            [com.github.metaphor/lein-flyway "6.0.0"]]

  ;; Flyway Database Migration configuration
  :flyway {;; Optional - When a config path is passed, that config will be used in place of the below options.
           :driver "org.postgresql.Driver"
           :url "jdbc:postgresql://db:5432/dev"
           :user "dev"
           :password "dev"
           :locations ["filesystem:/usr/src/app/resources/migrations"]
           :encoding "UTF-8"}

  :ring {:handler clj-test.handler/app}
  
  :profiles { :test {:resource-paths ["resources/test"]}
              :dev
                    {:resource-paths ["resources/dev"]
                     :dependencies [[javax.servlet/servlet-api "2.5"]
                                    [ring/ring-mock "0.3.1"]
                                    ]}}

  :eastwood {:exclude-linters [:constant-test]
             :include-linters [:deprecations]
             :exclude-namespaces [clojure-rest.config.routes]
             ;:debug [:all]
             ;:out "eastwood-warnings.txt"
             })

