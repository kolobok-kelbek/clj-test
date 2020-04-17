(ns clj-test.config.swagger)

(def swagger-routes
  {:swagger
   {:ui "/api/docs"
    :spec "/api/swagger.json"
    :data {:info {:title "Simple Clojure API"
                  :description "Example Clojure api for Ippon Blog Article"}
           :tags [{:name "api-documents", :description "Api document endpoints"}]}}})

