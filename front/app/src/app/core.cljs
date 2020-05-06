(ns app.core
    (:require
      [reagent.core :as r :refer [atom]]
      [reagent.dom :as d]
      [ajax.core :refer [GET POST]]))

(def num-of-pos 10)
(def offset-default 0)

(def state (r/atom {:data {} :pagination {:offset offset-default :current-page 1 :num-of-pages 0 :max-offset 0}}))

;; -------------------------
;; Views

(enable-console-print!)

(defn get-total
  []
  (:total (:meta (:data @state))))

(defn get-data
  []
  (:data (:data @state)))

(defn get-meta
  []
  (:meta (:data @state)))

(defn get-offset
  []
  (:offset (:pagination @state)))

(defn get-max-link
  []
  (let [n (/ (float (get-total)) num-of-pos)]
    (if (< 6 n) 6 n)))

(defn get-max-offset
  []
  (:max-offset (:pagination @state)))

(defn get-current-page
  []
  (:current-page (:pagination @state)))

(defn num-of-pages
  []
  (:num-of-pages (:pagination @state)))

(defn table
  []
  [:table.table.table-hover
    [:thead
      [:tr
        [:th {:scope "col"} "First name"]
        [:th {:scope "col"} "Gender"]
        [:th {:scope "col"} "Date of Birth"]
        [:th {:scope "col"} "Address"]
        [:th {:scope "col"} "OMS number"]
        [:th {:scope "col"} [:button.btn.btn-success {:on-click handle-click} [:i.fas.fa-user-plus]]]]]
    [:tbody
      (for [item (get-data)]
        [:tr
          [:th (:full_name item)]
          [:th (:gender item)]
          [:th (:dob item)]
          [:th (:address item)]
          [:th (:oms_number item)]
          [:th 
            [:div.btn-group {:role "group"}
              [:button.btn.btn-info {:type "button"} [:i.fas.fa-user-edit]]
              [:button.btn.btn-danger {:type "button"} [:i.fas.fa-user-times]]]]])]])

(defn nav-link
  [link icon params]
  [:li.page-item params
    [:a.page-link {:href link} icon]])

(defn handle-click [event] ;an event object is passed to all events
  (js/alert "fdsa"))

(defn pagination-start
  []
  (let [current-page (get-current-page)]
    (if (<= (- current-page 2) 0) 1 (- current-page 2))))

(defn pagination-end
  []
  (let [current-page (get-current-page)]
    (if (< (+ current-page 2) (num-of-pages))
      (if (<= (- current-page 2) 0) 6 (+ current-page 3))
      (inc (num-of-pages)))))

(defn pagination
  []
  [:nav
    [:ul.pagination.justify-content-center
      [nav-link "#" [:i.fas.fa-fast-backward]
        (if (> (get-offset) 0)
          {:on-click #(get-patients)}
          {:class "disabled"})]
      [nav-link "#" [:i.fas.fa-step-backward]
        (if (> (get-offset) 0)
          {:on-click #(get-patients (- (get-offset) num-of-pos))}
          {:class "disabled"})]
      
      (for [i (range (pagination-start) (pagination-end))]
        (if (= i (get-current-page))
          [nav-link "#" [:b i] {:class "disabled"}]
          [nav-link "#" [:b i] {:on-click #(get-patients (* (dec i) num-of-pos))}]))
      
      [nav-link "#" [:i.fas.fa-step-forward] 
        (if (not= (get-offset) (get-max-offset)) 
          {:on-click #(get-patients (+ (or (get-offset) 0) num-of-pos))}
          {:class "disabled"})]
      [nav-link "#" [:i.fas.fa-fast-forward]
        (if (not= (get-offset) (get-max-offset))
          {:on-click #(get-patients (get-max-offset))}
          {:class "disabled"})]]])

(defn get-patients
  [offset limit]
  (GET "/api/patients" {:params {:offset (or offset 0)
                                 :limit (or limit 10)}
                :handler (fn [r] (reset! state {:data r
                                                :pagination {:offset (or offset 0)
                                                             :current-page (int (Math/ceil (/ (if (> offset 0) (+ offset 1) 1) num-of-pos)))
                                                             :num-of-pages (int (Math/ceil (/ (:total (:meta r)) num-of-pos)))
                                                             :max-offset (* (int (Math/floor (/ (:total (:meta r)) num-of-pos))) num-of-pos)}}))
                :error-handler (fn [r] (prn r))
                :response-format :json
                :keywords? true}))

(defn home-page []
  (get-patients)
  (fn []
    [:main.container
      [:h1.mt-5.mb-5 "Patient manager"]
      [table]
      [pagination]]))

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
