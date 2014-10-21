(ns clj-canary-plug.core
  (:use [clojure.tools.nrepl.server :only (start-server stop-server)])
  (:import [net.canarymod.plugin.Priority]
           [net.canarymod.logger.Logman]))

(defn handlePlayerConnected [hook]
  (.info (net.canarymod.logger.Logman/getLogman "ClojurePlugin") "new player connected"))

(def config [["net.canarymod.hook.player.ConnectionHook" 'clj-canary-plug.core/handlePlayerConnected  net.canarymod.plugin.Priority/NORMAL]])

(defn lookup-handler [sym]
  (fn [& args] (apply (resolve sym) args)))

(defn register [plugin]
  (fn [[hookName handlerSym priority]]
    (.registerHook plugin hookName (lookup-handler handlerSym) priority)))

(defn enablePlugin [plugin]
  (doall (map (register plugin) config))
  (defonce server (start-server :port 7888)))
