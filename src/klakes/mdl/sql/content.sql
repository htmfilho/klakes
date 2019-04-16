-- :name find-by-reference :? :*
select * from content where reference = :reference

-- :name find-by-concept :? :*
select c.* from content c left join concept_content cc on c.id = cc.content 
where cc.concept = :concept