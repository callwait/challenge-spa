(ns scramble-spa.api
  (:require [clojure.test :refer :all]
            [scramble-spa.core :refer :all]
            [ring.mock.request :as mock]))

(deftest api-handler-test
  (testing "Test 1: rekqodlw / world"
    (is (= (:body (dev-app (mock/request :get "/api" {:s1 "rekqodlw"
                                                      :s2 "world"})))
           "success")))
  (testing "Test 2: cedewaraaossoqqyt / codewars"
    (is (= (:body (dev-app (mock/request :get "/api" {:s1 "cedewaraaossoqqyt"
                                                      :s2 "codewars"})))
           "success")))
  (testing "Test 3: katas / steak"
    (is (= (:body (dev-app (mock/request :get "/api" {:s1 "katas"
                                                      :s2 "steak"})))
           "fail")))
  (testing "Test 3: fo / foo"
    (is (= (:body (dev-app (mock/request :get "/api" {:s1 "fo"
                                                      :s2 "foo"})))
           "fail"))))
