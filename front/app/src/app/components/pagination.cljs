(ns app.components.pagination
    (:require
      [reagent.core :as r :refer [atom]]
      [app.utils.bootstrap-wrapper :as b]
      [app.utils.patient-client :as pc]))

(def num-of-pos 10)
(def offset-default 0)
(def deviation 2)

(def state (r/atom {:offset offset-default :current-page 1 :num-of-pages 0 :max-offset 0}))

(defn update-state
  [offset total]
  (reset! state {:offset offset
                 :current-page (int (Math/ceil (/ (if (> offset 0) (inc offset) 1) num-of-pos)))
                 :num-of-pages (int (Math/ceil (/ total num-of-pos)))
                 :max-offset (* (int (Math/floor (/ total num-of-pos))) num-of-pos)}))

(defn get-offset
  []
  (:offset @state))

(defn get-max-offset
  []
  (:max-offset @state))

(defn get-current-page
  []
  (:current-page @state))

(defn num-of-pages
  []
  (:num-of-pages @state))

(defn pagination-start
  []
  (let [current-page (get-current-page)]
    (if (< (+ current-page deviation) (num-of-pages))
      (if (<= (- current-page deviation) 0) 1 (- current-page deviation))
      (- current-page (- 4 (- (num-of-pages) current-page))))))

(defn pagination-end
  []
  (let [current-page (get-current-page)]
    (if (< (+ current-page deviation) (num-of-pages))
      (if (<= (- current-page deviation) 0) 6 (+ current-page 3))
      (inc (num-of-pages)))))

(defn nav-link
  [link icon params]
  [:li.page-item params
    [:a.page-link {:href link} icon]])

(defn get-patients
  [offset limit handler]
  (swap! state assoc :offset offset)
  (pc/get-patients offset limit handler))

(defn view
  [handler]
  [:> b/pagination {:class "justify-content-center"}
    [:> b/pagination.First {:on-click #(get-patients 0 num-of-pos handler)
                            :disabled (if (<= (get-offset) 0) true false)}]
    [:> b/pagination.Prev {:on-click #(get-patients (- (get-offset) num-of-pos) num-of-pos handler)
                           :disabled (if (<= (get-offset) 0) true false)}]

    (for [i (range (pagination-start) (pagination-end))]
      [:> b/pagination.Item {:on-click #(get-patients (* (dec i) num-of-pos) num-of-pos handler)
                             :active (if (= i (get-current-page)) true false)} i])

    [:> b/pagination.Next {:on-click #(get-patients (+ (or (get-offset) 0) num-of-pos) num-of-pos handler)
                           :disabled (if (= (get-offset) (get-max-offset)) true false)}]
    [:> b/pagination.Last {:on-click #(get-patients (get-max-offset) num-of-pos handler)
                           :disabled (if (= (get-offset) (get-max-offset)) true false)}]])

