(ns immutant.init
  (:require [immutant.messaging :as msg]))

(def q "/queue/foo")
(msg/start q, :durable false)

(defn handler [msg]
  (println "1" msg)
  (Thread/sleep (+ 1000 (rand 1000)))
  (msg/publish q (str "1 " (java.util.Date.))))

(msg/listen q handler)

(msg/publish q "start")