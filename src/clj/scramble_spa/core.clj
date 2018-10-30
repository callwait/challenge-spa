(ns scramble-spa.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.string :as s]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :as response]))

(defn prepare-sting [str]
  (when-not (s/blank? str)
    (->> str
         (re-seq #"[a-z]+")
         first)))

(defn scramble-it [data]
  (->> data
       (map #(map (frequencies %) (second data)))
       (apply #(map (fn [x y]
                      (if x
                        (>= x y)
                        false)) %1 %2))
       (every? true?)))

(defn scramble? [s1 s2]
  (let [str1 (prepare-sting s1)
        str2 (prepare-sting s2)]
    (cond
      (or (not str1)
          (not str2)) false
      (scramble-it [str1 str2]) true
      :else false)))

(defroutes app-routes
           (route/resources "/" {:root "public"})
           (GET "/" [] (-> (response/resource-response "index.html" {:root "public"})
                           (response/content-type "text/html")))
           (GET "/api" [s1 s2] (if (scramble? s1 s2)
                                 "success"
                                 "fail"))
           (route/not-found "Not Found"))

(def dev-app (wrap-reload (wrap-defaults #'app-routes site-defaults)))
