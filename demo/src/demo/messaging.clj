(ns demo.messaging
  (:require [immutant.messaging :as msg]))

;;; create queue, if not already
(def q "queue")
(msg/stop q :force true)
(msg/start q)

;;; publish some messages
(msg/publish q :ping)
(msg/publish q {:a 1, :b [1 2 3]})
(msg/publish q 42, :priority :high)

;;; consume them
(let [messages (msg/message-seq q)]
  (if (= 42 (msg/receive q))
    (take 2 messages)))

;;; filter them
(def results (atom []))
(msg/listen q #(swap! results conj (:time %)), :selector "type='date'")
(msg/publish q (with-meta {:time (java.util.Date.)} {:type "date"}))
@results