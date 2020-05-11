(ns app.utils.bootstrap-wrapper
    (:require
      [cljsjs.react-bootstrap]))

;; Components

(def alert (.-Alert js/ReactBootstrap))

(def button (.-Button js/ReactBootstrap))

(def table (.-Table js/ReactBootstrap))

(def pagination (.-Pagination js/ReactBootstrap))

(def modal (.-Modal js/ReactBootstrap))

;; Form

(def form (.-Form js/ReactBootstrap))

(def form-check (.-FormCheck js/ReactBootstrap))

(def feedback (.-Feedback js/ReactBootstrap))

;; Layout
 
(def container (.-Container js/ReactBootstrap))

