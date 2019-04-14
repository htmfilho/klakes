(ns klakes.web.vis)

(defn concepts-to-nodes [concepts]
  (map #(zipmap [:id :shape :label] 
                [(:id %) "box" (:name %)]) 
                concepts))

(defn triples-to-edges [triples]
  (map #(zipmap [:from :to :arrows :font :label] 
                [(:subject %) (:object %) "to" {:align "top"} (:name %)]) 
                triples))