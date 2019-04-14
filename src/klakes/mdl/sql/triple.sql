-- :name delete-all :! :n
delete from triple

-- :name find-lakes :? :*
select * from concept where id in (
    select distinct(subject) from triple where parent is null union
    select distinct(object) from triple where parent is null)

-- :name find-by-parent :? :*
select * from concept where id in (
    select distinct(subject) from triple where parent = :parent union
    select distinct(object) from triple where parent = :parent)

-- :name find-by-subjects :? :*
select t.subject, t.object, p.name 
from triple t left join predicate p on t.predicate = p.id
where t.subject in (:v*:subjects)