-- :name find-by-reference :? :*
select * from content where reference = :reference

-- :name find-by-concept :? :*
select c.* from content c left join concept_content cc on c.id = cc.content 
where cc.concept = :concept

-- :name find-labels-frequence :? :*
select c.id, c.label, count(cc.content) as num
from concept c join concept_content cc on c.id = cc.concept 
group by concept 
order by c.label