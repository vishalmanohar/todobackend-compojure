(ns todobackend.todos)

(def todos (atom []))
(def todo_counter (atom 0))

(defn get-todos []
  @todos)

(defn get-todo [id]
  (first (filter  (fn [x] (= (get x "id") id)) @todos)))

(defn cleanup-todos []
  (do
    (reset! todos [])
    (reset! todo_counter 0)))

(defn inc-counter []
  (swap! todo_counter inc))

(defn add-todo [todo]
  (let [todo-id (str (inc-counter))]
    (do
    (swap! todos conj (assoc todo "id" todo-id "completed" false "url" (str "/" todo-id)))
    (get-todo todo-id)
    )))

(defn delete-todo [id]
  (reset! todos (filter (fn [x] (not= (get x "id") id)) @todos)))

(defn update-todo [todo]
  (do
    (delete-todo (get todo "id"))
    (swap! todos conj todo)
    (get-todo (get todo "id"))))
