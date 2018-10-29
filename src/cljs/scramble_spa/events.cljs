(ns scramble-spa.events
  (:require
    [re-frame.core :as rf]
    [clojure.string :as s]
    [scramble-spa.db :as db]))

(defn prepare-sting [db str]
  (-> (->> (get-in db [:challenge str])
           (re-find #"[a-z]+"))
      (s/split "")
      set))

(rf/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))

(rf/reg-event-db
  ::set-challenge
  (fn [db [_ type var]]
    (assoc-in db [:challenge type] var)))

(rf/reg-event-fx
  ::scramble-it
  (fn [{db :db}]
    (let [str1   (prepare-sting db :str1)
          str2   (prepare-sting db :str2)
          result (if (or (not str1)
                         (not str2))
                   false
                   (clojure.set/subset? str2 str1))]
      {:db (->> result
                (assoc db :challenge-result))})))
