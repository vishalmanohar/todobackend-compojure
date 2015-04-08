(ns todobackend.handler
  (:require [todobackend.todos :refer [get-todos, add-todo]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.json :as middleware]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults site-defaults]])

  )

(defn handle-add [body]
  (let [name (get body "name")]
    (response (add-todo name))
    ))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/todos" [] (response (get-todos)))
  (POST "/todo" {body :body}
    (handle-add body))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
    (middleware/wrap-json-body)
    (middleware/wrap-json-response)
    (wrap-defaults api-defaults)))

