(ns demo.web
  (:require [immutant.web            :as web]
            [immutant.web.session    :as immutant-session]
            [ring.middleware.session :as ring-session]
            [ring.util.response      :as ring-util]))

(defn request-dumper
  "A very simple ring handler"
  [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (with-out-str (pprint request))})
;;; Mount the handler relative to the app's context path [/demo]
(web/start #'request-dumper)



(defn world-greeter
  "An even simpler ring handler"
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "<h1>Hello World</h1>"})
;;; Mount it at a "sub context path" [/demo/foo]
(web/start "/foo" #'world-greeter)



(defn counter [{session :session}]
  (let [count (:count session 0)
        session (assoc session :count (inc count))]
    (-> (ring-util/response (str "You accessed this page " count " times."))
        (assoc :session session))))
;;; Use Immutant's session store for automatic replication
(web/start "/counter"
 (ring-session/wrap-session
  #'counter
  {:store (immutant-session/servlet-store)}))

