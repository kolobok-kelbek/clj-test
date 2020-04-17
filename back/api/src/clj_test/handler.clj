(ns clj-test.handler
    (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [clj-test.config.router :as config-router]))

(def app config-router/app-routes)

