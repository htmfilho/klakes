-- :name delete-all :! :n
delete from triple

-- :name find-lakes :? :*
select * from concept where label in (
    select distinct(subject) from triple where parent is null union
    select distinct(object) from triple where parent is null)