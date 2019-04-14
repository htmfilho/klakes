-- :name concepts-exist :? :1
select count(id) as c from concept;

-- :name find-by-label :? :*
select * from concept where label = :label