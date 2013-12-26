

/*DROP TABLE clickredirect; */
CREATE TABLE clickredirect (
  `testid` varchar(50),
  `datetime` datetime,
  `slot` varchar(50),
  `creative` varchar(50), 
  `releasetype` varchar(50),
  `platform` varchar(50),
  `version` varchar(50),
  `integration` varchar(50),
  `redirecturl` varchar(50),
  `status` varchar(50)
)


DROP TABLE clicks;
CREATE TABLE clicks (
  `testid` varchar(150),
  `datetime` datetime,
  `slot` varchar(50),
  `creative` varchar(50), 
  `releasetype` varchar(50),
  `platform` varchar(50),
  `version` varchar(50),
  `integration` varchar(50),  
  `thirdparty` varchar(50),
  `device` varchar(50),
  `status` varchar(50)
) 


DROP TABLE beacon;
CREATE TABLE beacon (
  `testid` varchar(150),
  `datetime` datetime,
  `slot` varchar(50),
  `creative` varchar(50), 
  `releasetype` varchar(50),
  `platform` varchar(50),
  `version` varchar(50),
  `integration` varchar(50),  
  `beacon` int(10),
  `device` varchar(50),
  `status` varchar(50)
)

delete from clicks where testid >= '*';
delete from beacon where testid > '*';

select * from beacon where testid='happyyang'
select * from clicks where testid='happyyang'





UPDATE clicks SET datetime='2013-07-31 23:46:28', thirdparty='yes' WHERE testid='iOS_5.1.1_SDK-3.7.0_320x480_320x480' and slot='320x480' and creative='320x480' and releasetype='imai_async';

SELECT * FROM clicks WHERE testid='testAndroid_4.2_sdk370' and slot='320x50' and creative='320X48' and releasetype='imai_async';

INSERT INTO `clicks` (`testid`, `datetime`, `slot`, `creative`, `releasetype`, `platform`, `version`, `integration`, `status`) VALUES ('testInterstitialAds_Async_Slot320x480_Creative320x480', '2013-06-13 15:41:13.616', '320x50', '216x36', 'imai', 'android', '4.2', 'sdk370', 'PASS'); 



select * from beacon where testid='AndroidTest1' and beacon=18

UPDATE beacon SET datetime='2013-08-05 17:42:16' WHERE testid='iOS_6.0.1_SDK-3.7.0_iPhone_320x480_320x480' and slot='320x480' and creative='320x480' and releasetype='imai_async' and device='iPhone' and beacon='18';

UPDATE clicks SET status='PASS' where testid=2

INSERT INTO `beacon` (`testid`, `datetime`, `slot`, `creative`, `releasetype`, `platform`, `version`, `integration`, `beacon`, `device`, `status`) VALUES ('AndroidTest1', '2013-06-13 15:41:13.616', '320x480', '320x480', 'imai_sync', 'iOS', '6.0.1', 'SDK-3.7.0', 777, 'iPhone', 'PASS'); 

/* Queries to fetch data for results presentation */

select * from clicks;
select * from beacon;

select distinct(slot) from clicks order by slot asc

select c.*, b.beacon as beacon_resp from clicks c inner join beacon b on c.testid = b.testid
