(ns clj-test.handler-test
  (:use re-rand)
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clj-test.handler :refer :all]
            [clojure.java.jdbc :as jdbc]
            [cheshire.core :as json]
            [clojure.data.generators :as gen]
            [faker.name :as faker-name]
            [faker.address :as faker-address]
            [clj-test.config.db :as db]
            [clj-test.migration :as migration]))

(defn get-full-name
  []
  (str (faker-name/first-name) " " (faker-name/last-name)))

(defn get-address
  []
  (faker-address/street-address))

(defn get-oms-number
  []
  (re-rand #"\d{16}"))

(defn get-gender
  []
  (rand-nth ["male" "female"]))

(defn get-date
  []
  (.format (new java.text.SimpleDateFormat "yyyy-MM-dd") (gen/date)))

(defn get-model
  []
  (let [model {:full_name (get-full-name) :gender (get-gender) :dob (get-date) :address (get-address) :oms_number (get-oms-number)}] model))

(deftest test-app
  (testing "not-found route"
    (let [response (app (mock/request :get "/api/invalid"))]
      (is (= (:status response) 404)))))


; ------------------
; -- Tests on get
; ------------------
(deftest test-patient-controller-list
  (migration/reset)

  (testing "Testing patient list route worked"
    (let [response (app (mock/request :get "/api/patients"))]
      (is (= (:status response) 200))
      (is (= (:headers response) {"Content-Type" "application/json; charset=utf-8", "Content-Length" "1880"}))
      (let [body (json/parse-string (slurp (:body response)) true)]
        (is (= (get (get body :meta) :total) 14))
        (is (= (count (get body :data)) 14)))))
  
  (testing "Testing patient list route worked"
    (let [response (app (mock/request :get "/api/patients?offset=0&limit=4"))]
      (is (= (:status response) 200))
      (is (= (:headers response) {"Content-Type" "application/json; charset=utf-8", "Content-Length" "552"}))
      (let [body (json/parse-string (slurp (:body response)) true)]
        (is (= (get (get body :meta) :total) 14))
        (is (= (count (get body :data)) 4)))))

  (testing "Testing patient list route worked"
    (let [response (app (mock/request :get "/api/patients?limit=4"))]
      (is (= (:status response) 200))
      (is (= (:headers response) {"Content-Type" "application/json; charset=utf-8", "Content-Length" "552"}))
      (let [body (json/parse-string (slurp (:body response)) true)]
        (is (= (get (get body :meta) :total) 14))
        (is (= (count (get body :data)) 4))))))

(deftest test-patient-controller-getbyid
  (migration/reset)

  (testing "Testing get patient by id route worked"
    (let [response (app (mock/request :get "/api/patients/1"))]
      (is (= (:status response) 200))))

  (testing "Testing get patient by id route failed with incorrect id"
    (let [response (app (mock/request :get "/api/patients/notfound"))]
      (is (= (:status response) 400))))

  (testing "Testing get patient by id route failed with incorrect id"
    (let [response (app (mock/request :get "/api/patients/99999999999"))]
      (is (= (:status response) 404)))))


; ------------------
; -- Tests on create
; ------------------

(def patient-to-create
  (get-model))

(deftest test-patient-controller-create
  (migration/reset)
  
  (println patient-to-create)
  
  (testing "Testing patient creation route worked"
    (let [request (mock/request :post "/api/patients" (json/generate-string patient-to-create))]
    (let [response (app (mock/content-type request "application/json"))]
      (let [response-body (slurp (:body response))]
        (is (= (:status response) 200))
        (is (= (get (json/parse-string response-body) "full_name") (:full_name patient-to-create)))
        (is (= (get (json/parse-string response-body) "address") (:address patient-to-create)))
        (is (= (count (jdbc/query db/db-spec ["select * from patients"])) 15)))))))

 ; (testing "Testing document creation route failed because of incorrect document"
 ;   (let [request (mock/request :post "/documents" (json/generate-string {:id_document "dummy" :title "newDocumentTitle" :description "newDocumentText" :wrongattribute "badvalue"}))]
 ;   (let [response (app (mock/content-type request "application/json"))]
 ;     (is (= (:status response) 400))))))

