(ns clj-test.config.swagger)

(def swagger-routes
  {:swagger
   {:ui "/api/docs"
    :spec "/api/swagger.json"
    :data {:info {:title "Swagger documentation to clojure test task"
                  :description "Swagger documentation to clojure test task about patients"}
           :tags [{:name "api-patients", :description "Api patients endpoints"}]}}})

