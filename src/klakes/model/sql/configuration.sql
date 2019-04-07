-- :name find-by-key :? :1
select value from configuration where key = :key

-- :name update-parameter :! :n
update configuration set value = :value where key = :key
