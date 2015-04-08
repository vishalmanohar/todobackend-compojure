(ns todobackend.todos)

(def todos (atom [{:name "Todo 1"} {:name "Todo 2"}]))

(defn get-todos []
  @todos)

(defn add-todo [name]
  (swap! todos conj {:name name}))