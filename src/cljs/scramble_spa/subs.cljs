(ns scramble-spa.subs
  (:require
    [re-frame.core :as rf]))

(rf/reg-sub
  ::challenge
  (fn [db [_ type]]
    (get-in db [:challenge type])))

(rf/reg-sub
  ::challenge-result
  (fn [db]
    (get db :challenge-result)))
