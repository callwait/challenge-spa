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
       first
       set)))

(defn scramble? [s1 s2]
  (str s1 ", " (prepare-sting s1))
  (let [str1 (prepare-sting s1)
        str2 (prepare-sting s2)]
    (cond
      (or (not str1)
          (not str2)) "fail"
      (clojure.set/subset? str2 str1) "success"
      :else "fail")))

(defroutes app-routes
           (route/resources "/" {:root "public"})
           (GET "/" [] (-> (response/resource-response "index.html" {:root "public"})
                           (response/content-type "text/html")))
           (GET "/api" [s1 s2] (scramble? s1 s2))
           (route/not-found "Not Found"))

(def dev-app (wrap-reload (wrap-defaults #'app-routes site-defaults)))
