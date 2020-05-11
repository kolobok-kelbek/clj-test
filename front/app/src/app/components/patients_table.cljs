(ns app.components.patients-table
    (:require
      [reagent.core :as r :refer [atom]]
      [app.utils.bootstrap-wrapper :as b]
      [app.components.pagination :as pagination]
      [app.components.add-patient-modal :as add-patient-modal]
      [app.components.update-patient-modal :as update-patient-modal]
      [app.utils.patient-client :as pc]
      [app.components.add-patient-modal :as add-patient-modal]
      [app.components.update-patient-modal :as update-patient-modal]
      [app.utils.logger :as logger]))

(def state (r/atom {}))

(defn get-total
  []
  (:total (:meta (:data @state))))

(defn get-data
  []
  (:data @state))

(defn get-meta
  []
  (:meta @state))

(defn response-handler
  [r]
  (let [offset (or (:offset @pagination/state) 0)]
    (logger/log offset)
    (reset! state r)
    (pagination/update-state offset (:total (:meta r)))
    (logger/log (str @pagination/state))
    (js/window.history.pushState {} "" (str "?offset=" offset "&limit=" pagination/num-of-pos))))

(defn table-update
  []
  (pc/get-patients (:offset @pagination/state) pagination/num-of-pos response-handler))

(defn row [label input]
  [:div.form-group
    [:label label]
    input])

(defn query-to-keywords [req]
  (into {} (for [[_ k v] (re-seq #"([^&=]+)=([^&]+)" req)]
    [(keyword k) v])))

(defn view
  []
  (let [params (query-to-keywords (subs (-> js/window .-location .-search) 1))]
    (swap! pagination/state assoc :offset (or (int (:offset params)) 0))
    (pc/get-patients (or (int (:offset params)) 0) (or (:limit params) pagination/num-of-pos) response-handler))
  (fn [] [:div
    [:> b/table {:striped true :bordered true :hover true}
      [:thead
        [:tr
          [:th "First name"]
          [:th "Gender"]
          [:th "Date of Birth"]
          [:th "Address"]
          [:th "OMS number"]
          [:th [:> b/button {:variant "success" :on-click add-patient-modal/handleShow} [:i.fas.fa-user-plus]]]]]
      [:tbody
        (for [item (get-data)]
          [:tr
            [:td (:full_name item)]
            [:td (:gender item)]
            [:td (:dob item)]
            [:td (:address item)]
            [:td (:oms_number item)]
            [:td 
              [:div.btn-group {:role "group"}
                [:button.btn.btn-info {:type "button"
                                       :on-click #(update-patient-modal/handleShow item)}
                  [:i.fas.fa-user-edit]]
                [:button.btn.btn-danger {:type "button"
                                         :on-click #(pc/delete-patient
                                            (:id item)
                                            table-update)} 
                  [:i.fas.fa-user-times]]]]])]]
      [pagination/view response-handler]
      [add-patient-modal/view table-update]
      [update-patient-modal/view table-update]]))

