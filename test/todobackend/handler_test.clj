(ns todobackend.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [todobackend.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))))

  (testing "todos endpoint"
    (let [response (app (mock/request :get "/1"))]
      (is (= (:status response) 200))))
  )
