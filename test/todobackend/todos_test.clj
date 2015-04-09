(ns todobackend.todos-test
  (:require [clojure.test :refer :all]
            [todobackend.todos :refer :all]))

(defn todo-test-fixture [f]
  (f)
  (cleanup-todos)
  )

(use-fixtures :each todo-test-fixture)

(deftest test-add-todos
  (testing "todos add todo"
    (is (= (add-todo {"name" "task1"}) {"name" "task1" "id" "1" "url" "/1" "completed" false}))
    (is (= (add-todo {"name" "task2"}) {"name" "task2" "id" "2"  "url" "/2" "completed" false}))))

(deftest test-update-todos
  (testing "todos update todo"
    (is (= (add-todo {"name" "task1"}) {"name" "task1" "id" "1"  "url" "/1" "completed" false}))
    (is (= (update-todo {"id" 1 "name" "new task name"}) {"id" 1 "name" "new task name"}))))

(deftest test-delete-todos
  (testing "todos delete todo"
    (is (= (do
             (add-todo {"name" "task1"})
             (add-todo {"name" "task2"})
             (delete-todo "1")
             )
          [ {"url" "/2", "completed" false, "id" "2", "name" "task2"}])))
  )
