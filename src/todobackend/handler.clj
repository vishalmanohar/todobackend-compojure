(ns todobackend.handler
  (:require [todobackend.todos :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.json :as middleware]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults site-defaults]]
            )
  )

(defn handle-add [body]
  (response (add-todo body)))

(defn handle-update [body]
  (response (update-todo body)))

(defn handle-delete [id]
  (do
    (delete-todo id)
    (response {:status "Ok"})))

(defn handle-delete-all []
  (do
    (cleanup-todos)
    (response {:status "Ok"})))

(defroutes app-routes
  (GET "/" [] (response (get-todos)))
  (OPTIONS "/*" [] (response ""))
  (POST "/" {body :body} (handle-add body))
  (PATCH "/" {body :body} (handle-update body))
  (DELETE "/:id" [id] (handle-delete id))
  (DELETE "/" [] (handle-delete-all))
  (GET "/:id" [id] (response (get-todo id)))
  (route/not-found "Not Found"))

(defn wrap-headers [handler]
  (fn [request]
    (let [response (handler request)]
      (-> response
        (assoc-in [:headers "Access-Control-Allow-Origin"] "*")
        (assoc-in [:headers "Access-Control-Allow-Methods"] "POST, GET, DELETE, PATCH, OPTIONS")
        (assoc-in [:headers "Access-Control-Allow-Headers"] "Content-Type")
        ))))

(def app
  (-> app-routes
    (middleware/wrap-json-body)
    (middleware/wrap-json-response)
    (wrap-defaults api-defaults)
    (wrap-headers)
    )
  )

