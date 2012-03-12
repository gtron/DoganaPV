-- MySQL dump 10.13  Distrib 5.1.47, for Win32 (ia32)
--
-- Host: localhost    Database: magazzini
-- ------------------------------------------------------
-- Server version	5.1.47-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `consegne`
--

DROP TABLE IF EXISTS `consegne`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consegne` (
  `idConsegna` int(11) NOT NULL AUTO_INCREMENT,
  `numero` int(11) DEFAULT '0',
  `idmerce` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `iter` tinyint(8) unsigned NOT NULL DEFAULT '0',
  `datacreazione` datetime NOT NULL DEFAULT '1900-01-01 00:00:00',
  `datachiusura` datetime DEFAULT NULL,
  `pesopolizza` double unsigned DEFAULT '0',
  `pesofinaleportocarico` tinyint(1) unsigned DEFAULT '0',
  `provenienza` varchar(255) NOT NULL DEFAULT '',
  `origine` varchar(255) NOT NULL DEFAULT '',
  `mezzo` varchar(255) NOT NULL DEFAULT '',
  `regimedoganale` varchar(100) NOT NULL DEFAULT '',
  `codicenc` varchar(45) NOT NULL DEFAULT '',
  `posizione` varchar(100) NOT NULL DEFAULT '',
  `tassocambio` double unsigned NOT NULL DEFAULT '0',
  `tassoumidita` double unsigned NOT NULL DEFAULT '0',
  `numpartitario` int(11) DEFAULT NULL,
  `isvalutaeuro` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `valoreunitario` double unsigned DEFAULT NULL,
  PRIMARY KEY (`idConsegna`)
) ENGINE=MyISAM AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consegne`
--

LOCK TABLES `consegne` WRITE;
/*!40000 ALTER TABLE `consegne` DISABLE KEYS */;
INSERT INTO `consegne` VALUES (29,301,1,1,'2011-06-25 14:34:33','2011-09-05 01:09:23',10062380,0,'-','-','Pascha','7100','26080000','EXT',1,8.886405,723,0,1),(30,0,120,4,'2011-06-25 20:07:00','2011-09-05 01:22:44',0,0,'-','-','-','7100','26080000','NAZ',1,10,754,0,1),(31,0,120,4,'2011-06-25 20:07:00','2011-09-05 01:23:05',0,0,'-','-','-','7110','26080000','NAZ',1,10,746,0,1),(33,165,6,4,'2011-06-26 21:46:42','2011-09-05 01:23:29',0,0,'-','-','-','7100','26080000','COM',1,7.65,750,0,1),(34,328,1,1,'2011-06-26 23:13:31',NULL,0,0,'-','-','Federal Franklin','7100','26080000','EXT',1,9.121618,725,0,1),(35,176,5,2,'2011-06-26 23:25:29',NULL,6977460,1,'-','-','Caribbean ID','4500','26080000','XNZ',1,8.4157,753,0,1),(36,195,12,1,'2011-06-26 23:30:50','2011-09-23 11:27:58',0,0,'-','-','Pur Navolok','7100','26080000','EXT',1,8.886315,755,0,1),(37,26,20,2,'2011-06-27 01:12:00',NULL,0,0,'-','-','CAfer Kalkavan','4500','26080000','XNZ',1,7.177072,747,0,1),(38,142,12,1,'2011-06-27 02:28:32','2011-09-05 01:23:50',0,0,'-','-','Margaretha','7100','26080000','EXT',1,9.107759,749,0,1),(39,231,12,1,'2011-09-05 01:34:07',NULL,3206000,0,'-','-','Theseus','7100','26080000','EXT',1.42,7.485519,760,0,1),(40,271,12,1,'2011-09-05 02:01:08',NULL,3500000,0,'-','-','Edzard Cirksena','7100','26080000','EXT',1,8.050539,762,0,1),(45,275,5,1,'2011-09-20 10:33:29',NULL,8982760,1,'Callao','Perù','Chollada Naree','7100','26080000','EXT',1.42,9.02587,763,0,1),(42,0,120,4,'2011-06-07 00:00:00','2011-09-05 02:11:18',0,0,'-','-','-','4500','26070000','NAZ',1,0,764,0,1),(43,0,120,4,'2011-06-22 00:00:00','2011-09-05 02:25:56',0,0,'-','-','-','4500','26070000','NAZ',1,0,758,0,1),(44,0,120,4,'2011-06-22 00:00:00',NULL,0,0,'-','-','-','altro','altro','NAZ',1,10,758,0,1),(46,277,1,1,'2011-09-20 10:44:32',NULL,28500000,0,'Kivalina','USA','Torm Trader','7100','26080000','EXT',1.42,8.579236,765,0,NULL),(47,278,1,1,'2011-09-20 11:31:11',NULL,4300000,0,'Kivalina','USA','Torm Trader','7100','26080000','EXT',1.42,9.5,766,0,NULL),(48,0,120,4,'2011-09-06 00:00:00',NULL,0,0,'Portovesme','Italia','camion','altro','altro','NAZ',1,4,767,0,NULL);
/*!40000 ALTER TABLE `consegne` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fatture`
--

DROP TABLE IF EXISTS `fatture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fatture` (
  `idFattura` int(11) NOT NULL AUTO_INCREMENT,
  `data` datetime DEFAULT NULL,
  `numero` int(11) NOT NULL DEFAULT '0',
  `isprovvisoria` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `valore` double NOT NULL DEFAULT '0',
  `valoredollari` double NOT NULL DEFAULT '0',
  `cambio` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`idFattura`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fatture`
--

LOCK TABLES `fatture` WRITE;
/*!40000 ALTER TABLE `fatture` DISABLE KEYS */;
/*!40000 ALTER TABLE `fatture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iter`
--

DROP TABLE IF EXISTS `iter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(250) NOT NULL DEFAULT '',
  `descrizione` varchar(250) NOT NULL DEFAULT '',
  `ispesofattura` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `ispesobolla` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `regdoganale` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `regiva` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `hasrettifica` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `singolicarichi` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `queryin` text,
  `queryout` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iter`
--

LOCK TABLES `iter` WRITE;
/*!40000 ALTER TABLE `iter` DISABLE KEYS */;
INSERT INTO `iter` VALUES (1,'ExtraComunitaria','Si considera il Peso della Bolla Doganale, Immissione in Reg.Doganale con IM7, passaggo a R.Iva con IM4',0,1,1,1,1,0,'select  min( data )  as mindata , sum(netto)  as PesoNetto,  merce, cliente, fornitore, destinazione\r\n from `archivio corretto` where \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  %ANDS%\r\ngroup by  cliente,  merce, `num consegna` , `num documento`, fornitore, destinazione\r\norder by  min( data )','select   data   , sum( IIF(fornitore = \'0\'  , - netto , netto)) as PesoNetto,  merce , \'%S%\' as fornitore  , destinazione \r\n from `archivio corretto` where \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  and \r\n(  fornitore = \'%S%\' or ( cliente =  \'%S%\'  and destinazione = 8 ) )\r\nand data =  #%D%#\r\ngroup by  data , fornitore ,  merce, `num consegna` , `num documento`,  IIF(fornitore = \'0\'  , cliente, fornitore ), destinazione '),(2,'EC Nazionalizzata','Si considera il Peso della Bolla Doganale, Immissione direttamente in Reg.Iva con IM4',0,1,0,1,1,0,'select  min( data )  as mindata , sum(netto)  as PesoNetto,  merce, cliente, fornitore, destinazione\r\n from `archivio corretto` where \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  %ANDS%\r\ngroup by  cliente,  merce, `num consegna` , `num documento`, fornitore, destinazione\r\norder by  min( data )','select   data   , sum( IIF(fornitore = \'0\'  , - netto , netto)) as PesoNetto,  merce , \'%S%\' as fornitore  , destinazione \r\n from `archivio corretto` where \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  and \r\n(  fornitore = \'%S%\' or ( cliente =  \'%S%\'  and destinazione = 8 ) )\r\nand data =  #%D%#\r\ngroup by  data , fornitore ,  merce, `num consegna` , `num documento`,  IIF(fornitore = \'0\'  , cliente, fornitore ), destinazione '),(3,'Comunitaria Glencore','Si considera il Peso della Fattura, Immissione direttamente in Reg.Iva senza IM4',1,0,0,1,1,0,'select  min( data )  as mindata , sum(netto)  as PesoNetto,  merce, cliente, fornitore, destinazione\r\n from `archivio corretto` where \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  %ANDS%\r\ngroup by  cliente,  merce, `num consegna` , `num documento`, fornitore, destinazione\r\norder by  min( data )','select   data   , sum( IIF(fornitore = \'0\'  , - netto , netto)) as PesoNetto,  merce , \'%S%\' as fornitore  , destinazione \r\n from `archivio corretto` where \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  and \r\n(  fornitore = \'%S%\' or ( cliente =  \'%S%\'  and destinazione = 8 ) )\r\nand data =  #%D%#\r\ngroup by  data , fornitore ,  merce, `num consegna` , `num documento`,  IIF(fornitore = \'0\'  , cliente, fornitore ), destinazione '),(4,'Comunitaria NON Glencore','Entra in Reg.Doganale con i singoli pesi, non passa in R.IVA',0,0,1,0,0,1,'select sum(netto) as PesoNetto, data,  merce,  `num consegna` , \'%S%\' as cliente, destinazione\r\n from `archivio corretto` where \r\ndata = #%D%# and\r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  and \r\n( cliente  = \'%S%\'\r\nor ( fornitore = \'%S%\'  and destinazione = 8 ) )\r\ngroup by data, merce, `num consegna`, destinazione','select sum( IIF(fornitore = \'0\'  , - netto , netto))  as PesoNetto , data  , merce,  `num consegna`   \r\n, \'%S%\' as fornitore  , destinazione \r\nfrom `archivio corretto` where \r\ndata = #%D%# and \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  and \r\n(  fornitore = \'%S%\' or ( cliente =  \'%S%\'  and destinazione = 8 ) )\r\ngroup by data, merce, `num consegna` , IIF(fornitore = \'0\'  , cliente, fornitore ), destinazione ');
/*!40000 ALTER TABLE `iter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merci`
--

DROP TABLE IF EXISTS `merci`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merci` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL DEFAULT '',
  `codquadrelli` varchar(10) NOT NULL DEFAULT '',
  `iterdefault` tinyint(8) unsigned DEFAULT '0',
  `colore` varchar(10) DEFAULT NULL,
  `speciecolli` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1378 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merci`
--

LOCK TABLES `merci` WRITE;
/*!40000 ALTER TABLE `merci` DISABLE KEYS */;
INSERT INTO `merci` VALUES (1120,'Ossidi Test','120',4,'#F5FE83','rinfusa'),(1375,'Solfato di piombo','375',4,'#0808AC','rinfusa'),(1005,'Blenda Colquisiri','005',1,'#F5DEB3','rinfusa'),(1001,'Blenda RedDog','001',1,'#F5DEB3','rinfusa'),(1050,'Galena Guemassa','050',1,'#F5FED3','rinfusa'),(306,'Copper Matte','306',0,'','rinfusa'),(305,'Copper Dross S.G. sacconi','305',0,'','rinfusa'),(304,'Copper Dross 2° Trattamento','304',0,'','rinfusa'),(303,'Copper Dross 1° Trattamento','303',0,'','rinfusa'),(299,'Pani PB/Bi 3% S.Gavino','299',0,'','rinfusa'),(298,'Pani Pb/Bi 1% S.Gavino','298',0,'','rinfusa'),(297,'Piombo antimoniale(Pb Sb)','297',0,'','rinfusa'),(296,'Piombo termico 99,985 per Elettrolisi','296',0,'','rinfusa'),(295,'Piombo da rec. schiume S.Gavino','295',0,'','rinfusa'),(294,'Pb 99,99 S.Gavino per Elettrolisi','294',0,'','rinfusa'),(293,'Pb 99,97 S.Gavino per Vendita','293',0,'','rinfusa'),(292,'Pani Piombo/Bismuto S.Gavino','292',0,'','rinfusa'),(291,'Pani Pb/Ag S.Gavino','291',0,'','rinfusa'),(290,'Piombo termico 99,97% S.Gavino','290',0,'','rinfusa'),(286,'Schiume Pb da Lavaggio S.Gavino','286',0,'','rinfusa'),(285,'Sciumature di Zn A-ESSE','285',0,'','rinfusa'),(284,'Schiume Argentifere frantumate','284',0,'','rinfusa'),(283,'Schiume Pb/Ag PERTUSOLA','283',0,'','rinfusa'),(282,'Schiume martll. da reparto','282',0,'','rinfusa'),(281,'Schiume colata pani IS','281',0,'','rinfusa'),(280,'Schiume di piombo','280',0,'','rinfusa'),(279,'Schiume Argentifere pezzatura','279',0,'','rinfusa'),(278,'Schiume cuprif. KSS/ISF per CO','278',0,'','rinfusa'),(277,'Schiume da lavaggio SCO','277',0,'','rinfusa'),(276,'Schiume da lavaggio (HBT)','276',0,'','rinfusa'),(274,'Scorie SIMAR','274',0,'','rinfusa'),(271,'Zinc Skim. PROMETAL (CAPITELLI)','271',0,'','rinfusa'),(270,'Zamac Skim.PROMETAL (COSTANZA)','270',0,'','rinfusa'),(269,'Ecumes Zinc PROMETAL (A-ESSE)','269',0,'','rinfusa'),(268,'Rottami Vari','268',0,'','rinfusa'),(267,'Rottami di vetro al Pb','267',0,'','rinfusa'),(266,'Rottami mattoni refrattari','266',0,'','rinfusa'),(265,'Bricchette di Zama','265',0,'','rinfusa'),(264,'Rottame di Zn frantumato I.T.M.','264',0,'','rinfusa'),(263,'Rottame Zama ROTANFER','263',0,'','rinfusa'),(262,'Granella di Zn R.M.B.','262',0,'','rinfusa'),(261,'Rottame Zama ITALEGHE','261',0,'','rinfusa'),(260,'Tornitura di Zama F.I.M.','260',0,'','rinfusa'),(259,'Rottame di Zn frantumato (fusti)','259',0,'','rinfusa'),(258,'Granella di Zama METALSIDER','258',0,'','rinfusa'),(257,'Rottame Zama frant. SIDERADR','257',0,'','rinfusa'),(256,'Scraps di vari fornitori','256',0,'','rinfusa'),(255,'Rottame Zama COLMETAL','255',0,'','rinfusa'),(254,'Rottame Zama PYRECO','254',0,'','rinfusa'),(253,'Rottame Zama CAPITELLI','253',0,'','rinfusa'),(252,'Scraps ASSISI METALLI','252',0,'','rinfusa'),(251,'Rottame Zama R.M.B.','251',0,'','rinfusa'),(246,'Ceneri di pirite SCARLINO','246',0,'','rinfusa'),(245,'Ceneri di Zn REMET','245',0,'','rinfusa'),(244,'Ceneri di Zn Zinox (Alpine/sacconi)','244',0,'','rinfusa'),(243,'Ceneri di Zn ZINCOL Bellusco','243',0,'','rinfusa'),(242,'Ceneri Zn A.ESSE','242',0,'','rinfusa'),(241,'Ceneri ZINOX','241',0,'','rinfusa'),(240,'Ceneri di Zn (N.Eurozinco) in sacconi','240',0,'','rinfusa'),(235,'Polveri di ricircolo KSS','235',0,'','rinfusa'),(234,'Polveri di Zn RAMET (sacconi)','234',0,'','rinfusa'),(233,'Polveri di Zn agglomerato (fusti)','233',0,'','rinfusa'),(232,'Polveri di Zn SITINDUSTRIE','232',0,'','rinfusa'),(231,'PolverI di Zn PROMETAL','231',0,'','rinfusa'),(230,'Polveri di Zn CAPITELLI','230',0,'','rinfusa'),(229,'Polveri da elettrofiltro KSS','229',0,'','rinfusa'),(228,'Pastello Pb RECOBAT','228',0,'','rinfusa'),(227,'Pastello di Pb ECOBAT P.DUGNANO','227',0,'','rinfusa'),(226,'Pastello di Pb ECOBAT Marcianise','226',0,'','rinfusa'),(218,'Fumi acc. ORI MARTIN in container','218',0,'','rinfusa'),(217,'Fumi di Acciaieria AST Terni (SILOS)','217',0,'','rinfusa'),(216,'Fumi acc. ARVEDI in container','216',0,'','rinfusa'),(215,'Fumi  F.A.S.','215',0,'','rinfusa'),(214,'Fumi ASO Siderurgica (sacconi)','214',0,'','rinfusa'),(213,'Fumi acc. RIVA ACCIAIO in container','213',0,'','rinfusa'),(212,'Fumi acc. PROFILATINAVE in container','212',0,'','rinfusa'),(211,'Fumi acc. PITTINI F.Nord UD in container','211',0,'','rinfusa'),(210,'Fumi acc. DUFERCO in container','210',0,'','rinfusa'),(209,'Fumi acc. VENETE Sarezzo in container','209',0,'','rinfusa'),(208,'Fumi acc. CALVISANO in container','208',0,'','rinfusa'),(207,'Fumi acc. FERALPI in container','207',0,'','rinfusa'),(206,'Fumi acc. TRENTINA in container','206',0,'','rinfusa'),(205,'Fumi LUCCHINI Piombino','205',0,'','rinfusa'),(204,'Fumi acc. VENETE Sarezzo Bs','204',0,'','rinfusa'),(203,'Fumi acc. E.V.A.','203',0,'','rinfusa'),(202,'Fumi acc. GRIGOLI (sacconi)','202',0,'','rinfusa'),(201,'Fumi acc. LUCCHINI Sidermeccanica','201',0,'','rinfusa'),(200,'Fumi acc. RIVA ACCIAIO','200',0,'','rinfusa'),(199,'Fumi acc. CIVIDATE AL PIANO (sacconi)','199',0,'','rinfusa'),(198,'Fumi acc. BERTOLI SAFAU (sacconi)','198',0,'','rinfusa'),(197,'Fumi acc.  PITTIN Ferr. NORD Udine','197',0,'','rinfusa'),(196,'Fumi acc. BERTOLI SAFAU','196',0,'','rinfusa'),(195,'Fumi acc. UGINE S.A.','195',0,'','rinfusa'),(194,'Fumi acc. USINOR','194',0,'','rinfusa'),(193,'Fumi acc. SIDER SCAL','193',0,'','rinfusa'),(192,'Fumi acc. ISP di Cremona','192',0,'','rinfusa'),(191,'Fumi acc. ARVEDI (Cremona)','191',0,'','rinfusa'),(190,'Fumi HULBOLDT x Waelz','190',0,'','rinfusa'),(189,'Fumi HUMBOLDT x Kss','189',0,'','rinfusa'),(188,'Fumi acc. DALMINE','188',0,'','rinfusa'),(187,'Fumi HUMBOLDT S.Gavino','187',0,'','rinfusa'),(186,'Fumi acc. STEFANA (sacconi)','186',0,'','rinfusa'),(185,'Fumi acc. VENETE Padova','185',0,'','rinfusa'),(184,'Fumi acc. PROFILATINAVE','184',0,'','rinfusa'),(183,'Fumi acc. IRO','183',0,'','rinfusa'),(182,'Fumi acc. Terni (Dolciami)','182',0,'','rinfusa'),(181,'Fumi VALLESABBIA Servizi','181',0,'','rinfusa'),(180,'Fumi acc. VALBRUNA (Valli)','180',0,'','rinfusa'),(179,'Fumi acc. CIVIDATE al PIANO','179',0,'','rinfusa'),(178,'Fumi CIVIDALE (sacconi)','178',0,'','rinfusa'),(177,'Fumi Traf. GNUTTI (sacconi)','177',0,'','rinfusa'),(176,'Fumi acc. AFV BELTRAME','176',0,'','rinfusa'),(175,'Fumi acc. ALFA ACCIAI','175',0,'','rinfusa'),(174,'Fumi acc. A.S. TERNI (DEMONT)','174',0,'','rinfusa'),(173,'Fumi acciaieria DEMONT','173',0,'','rinfusa'),(172,'Fumi acciaieria STEFANA','172',0,'','rinfusa'),(171,'Fumi acciaieria CALVISANO','171',0,'','rinfusa'),(170,'Fumi acciaieria FERRERO','170',0,'','rinfusa'),(169,'Fumi PITTINI Ferr. NORD_Potenza','169',0,'','rinfusa'),(168,'Fumi DUFERCO SpA','168',0,'','rinfusa'),(167,'Fumi acciaieria FERALPI','167',0,'','rinfusa'),(166,'Fumi acciaieria BELTRAME-TORINO','166',0,'','rinfusa'),(165,'Fumi ORI MARTIN','165',0,'','rinfusa'),(164,'Fumi  ASCOMETAL','164',0,'','rinfusa'),(163,'Fumi acc. A.S. TERNI_Nimbus (sacconi)','163',0,'','rinfusa'),(162,'Fumi FORELLI PIETRO (sacconi)','162',0,'','rinfusa'),(161,'Fumi acc. VALLOUREC','161',0,'','rinfusa'),(160,'Fumi Met.S.Marco in sacc.(OX)','160',0,'','rinfusa'),(159,'Fumi Prandelli  L.& F. (sacconi)','159',0,'','rinfusa'),(158,'Fumi acc. Duferdofin SpA','158',0,'','rinfusa'),(157,'Fumi acc. Zn/Cu Bozzoli (sacc.)','157',0,'','rinfusa'),(156,'Fumi acc. Sid.Trentina','156',0,'','rinfusa'),(155,'Mix fumi acciaieria','155',0,'','rinfusa'),(154,'Mix miscela Waelz','154',0,'','rinfusa'),(146,'Ossidi S.Gavino HBT','146',0,'#F5FE83','rinfusa'),(145,'Ossidi S.Gavino  GRAFITE','145',0,'#F5FE83','rinfusa'),(144,'Ossidi S.Gavino SLA-6','144',0,'#F5FE83','rinfusa'),(143,'Ossidi S.Gavino Lega Fine','143',0,'#F5FE83','rinfusa'),(142,'Ossidi S.Gavino SSBB','142',0,'#F5FE83','rinfusa'),(141,'Ossidi S.Gavino LELF','141',0,'#F5FE83','rinfusa'),(140,'Ossidi S.Gavino FUA','140',0,'#F5FE83','rinfusa'),(139,'Ossidi S.Gavino PZS','139',0,'#F5FE83','rinfusa'),(138,'Ossidi S.Gavino MATT','138',0,'#F5FE83','rinfusa'),(137,'Ossidi S.Gavino SPR','137',0,'#F5FE83','rinfusa'),(136,'Ossidi S.Gavino SCUL','136',0,'#F5FE83','rinfusa'),(135,'Ossidi S.Gavino Ox Bi','135',0,'#F5FE83','rinfusa'),(134,'Ossidi S.Gavino SCUF','134',0,'#F5FE83','rinfusa'),(133,'Ossidi S.Gavino SLA','133',0,'#F5FE83','rinfusa'),(132,'Ossidi S.Gavino SCO','132',0,'#F5FE83','rinfusa'),(131,'Ossidi S.Gavino B.I.','131',0,'#F5FE83','rinfusa'),(130,'Ossido di Piombo S.Gavino','130',0,'#F5FE83','rinfusa'),(129,'Ossidi metalli P.Dugnano','129',0,'#F5FE83','rinfusa'),(128,'Ossido di calce','128',0,'#F5FE83','rinfusa'),(127,'Ossidi Waelz P.N.','127',0,'#F5FE83','rinfusa'),(126,'Ossido Waelz Cd','126',0,'#F5FE83','rinfusa'),(125,'Ox Waelz Tub.press.','125',0,'','rinfusa'),(124,'Ox Waelz torbida','124',0,'','rinfusa'),(123,'Ox P.Vesme PTZ','123',0,'','rinfusa'),(122,'Ox Waelz P.Vesme senza cloro da By Pass','122',0,'','rinfusa'),(121,'Ox Waelz P.Vesme solfatato','121',0,'','rinfusa'),(120,'Ox Waelz P.Vesme senza cloro','120',4,'#F5FE83','rinfusa'),(119,'Ossidi Misti Zn Nuova Eurozinco','119',0,'#F5FE83','rinfusa'),(118,'Ossidi di Zn Colmetal (sacconi)','118',0,'#F5FE83','rinfusa'),(117,'Ossidi Zn Sitindustrie (sacconi)','117',0,'#F5FE83','rinfusa'),(116,'Ossidi Concentrati di Zinco','116',0,'#F5FE83','rinfusa'),(115,'Mix Ox P.nossa/Clinker','115',0,'','rinfusa'),(114,'Mix Ox P.nossa/fini','114',0,'','rinfusa'),(113,'Ossido WAELZ tipo 2','113',0,'#F5FE83','rinfusa'),(112,'Ossidi di Zn Commerciale Zinox','112',0,'#F5FE83','rinfusa'),(111,'Ossidi di Zinco (SIMAR)','111',0,'#F5FE83','rinfusa'),(110,'Ossidi di Zinco COLMETAL','110',0,'#F5FE83','rinfusa'),(109,'Ossidi misti ECO-ZINDER','109',0,'#F5FE83','rinfusa'),(108,'Ossido WAELZ prod. P_VESME','108',0,'#F5FE83','rinfusa'),(107,'Ossidi KSS','107',0,'#F5FE83','rinfusa'),(106,'Ossido di RAFFINAZIONE','106',0,'#F5FE83','rinfusa'),(105,'Ossidi di zinco Traf. C GNUTTI sacconi','105',0,'#F5FE83','rinfusa'),(104,'Ossidi di zinco Met. S. Marco in sacconi','104',0,'#F5FE83','rinfusa'),(103,'Ossidi di zinco Eredi Gnutti in sacconi','103',0,'#F5FE83','rinfusa'),(102,'Ossidi di zinco Almag in sacconi','102',0,'#F5FE83','rinfusa'),(99,'Coke Metallurgico','099',0,'','rinfusa'),(98,'Coke 40/80 mm Ucraino','098',0,'','rinfusa'),(97,'Fini di coke KSS','097',0,'','rinfusa'),(96,'Fini di coke IS','096',0,'','rinfusa'),(95,'Antracite','095',0,'','rinfusa'),(94,'Coke di petrolio','094',0,'','rinfusa'),(93,'Coke 60/100','093',0,'','rinfusa'),(92,'Coke 40/100','092',0,'','rinfusa'),(91,'Coke 40/90','091',0,'','rinfusa'),(90,'Coke 25/80','090',0,'','rinfusa'),(89,'Coke 30/80','089',0,'','rinfusa'),(88,'Coke 10/30','088',0,'','rinfusa'),(87,'Coke 10/25','087',0,'','rinfusa'),(86,'Coke 6/25','086',0,'','rinfusa'),(82,'Minerale  Zn CALCINATO','082',0,'','rinfusa'),(81,'Misti ALMAGRERA','081',0,'','rinfusa'),(80,'Misti PERUVIANI','080',0,'','rinfusa'),(79,'Misti GREEN CREEK','079',0,'','rinfusa'),(78,'Misti MOUNT ISA','078',0,'','rinfusa'),(77,'Misti  Mc ARTHUR RIVER','077',0,'','rinfusa'),(76,'Misti BRUNSWICK','076',0,'','rinfusa'),(70,'Galena  ZAMANTI','070',0,'#F5FED3','rinfusa'),(69,'Mix GALENE','069',0,'','rinfusa'),(68,'Galena EL MOCHITO','068',0,'#F5FED3','rinfusa'),(67,'Galena TURCA','067',0,'#F5FED3','rinfusa'),(66,'Galena YENICE','066',0,'#F5FED3','rinfusa'),(65,'Galena QUIRUVILVA','065',0,'#F5FED3','rinfusa'),(64,'Galena EX BOLIDEN  Pb 1','064',0,'#F5FED3','rinfusa'),(63,'Galena AKDAG','063',0,'#F5FED3','rinfusa'),(62,'Galena PERUVIANA','062',0,'#F5FED3','rinfusa'),(61,'Galena CANNINGTON','061',0,'#F5FED3','rinfusa'),(60,'Galena GREEN CREEK','060',0,'#F5FED3','rinfusa'),(59,'Galena RAURA','059',0,'#F5FED3','rinfusa'),(58,'Galena ROSH PINAH','058',0,'#F5FED3','rinfusa'),(57,'Galena VOLCAN','057',0,'#F5FED3','rinfusa'),(56,'Galena ATACOCHA','056',0,'#F5FED3','rinfusa'),(55,'Galena TARA','055',0,'#F5FED3','rinfusa'),(54,'Galena SILIUS','054',0,'#F5FED3','rinfusa'),(53,'Galena MENKA','053',0,'#F5FED3','rinfusa'),(52,'Galena BERONER','052',0,'#F5FED3','rinfusa'),(51,'Galena MILPO','051',0,'#F5FED3','rinfusa'),(50,'Galena GUEMASSA','050',1,'#F5FED3','rinfusa'),(49,'Galena CASSANDRA','049',0,'#F5FED3','rinfusa'),(48,'Galena BOUGRINE','048',0,'#F5FED3','rinfusa'),(47,'Galena BOLIVIANA','047',0,'#F5FED3','rinfusa'),(46,'Galena BRUNSWICH','046',0,'#F5FED3','rinfusa'),(45,'Galena DJEBEL DJERISSA','045',0,'#F5FED3','rinfusa'),(44,'Galena ALMAGRERA','044',0,'#F5FED3','rinfusa'),(43,'Galena AGGENEYS','043',0,'#F5FED3','rinfusa'),(42,'Galena RED DOG','042',0,'#F5FED3','rinfusa'),(41,'Galena LISHEEN','041',0,'#F5FED3','rinfusa'),(34,'Blenda HUANZALA','034',0,'#F5DEB3','rinfusa'),(33,'Mix BLENDE','033',0,'','rinfusa'),(32,'Blenda LISHEEN','032',0,'#F5DEB3','rinfusa'),(31,'Blenda CANNINGTON','031',0,'#F5DEB3','rinfusa'),(30,'Blenda GALMOY','030',0,'#F5DEB3','rinfusa'),(29,'Blenda DJEBEL DJERISSA','029',0,'#F5DEB3','rinfusa'),(28,'Blenda LARONDE','028',0,'#F5DEB3','rinfusa'),(27,'Blenda CINESE HECHI','027',0,'#F5DEB3','rinfusa'),(26,'Blenda PORCO','026',0,'#F5DEB3','rinfusa'),(25,'Blenda ISCAYCRUZ','025',0,'#F5DEB3','rinfusa'),(24,'Blenda EL BROCAL','024',0,'#F5DEB3','rinfusa'),(23,'Blenda RAMPURA AGUCHA','023',0,'#F5DEB3','rinfusa'),(22,'Blenda VOLCAN','022',0,'#F5DEB3','rinfusa'),(21,'Blenda BOLIVAR','021',0,'#F5DEB3','rinfusa'),(20,'Blenda CAYELI','020',0,'#F5DEB3','rinfusa'),(19,'Blenda BOLIVIANA','019',0,'#F5DEB3','rinfusa'),(18,'Blenda PERUVIANA','018',0,'#F5DEB3','rinfusa'),(17,'Blenda MOUNT ISA','017',0,'#F5DEB3','rinfusa'),(16,'Blenda TURCA','016',0,'#F5DEB3','rinfusa'),(15,'Blenda HUARON','015',0,'#F5DEB3','rinfusa'),(14,'Blenda CENTURY','014',0,'#F5DEB3','rinfusa'),(13,'Blenda HUARI HUARI','013',0,'#F5DEB3','rinfusa'),(12,'Blenda GUEMASSA','012',0,'#F5DEB3','rinfusa'),(11,'Blenda QUIRUVILCA','011',0,'#F5DEB3','rinfusa'),(10,'Blenda EL MOCHITO','010',0,'#F5DEB3','rinfusa'),(9,'Blenda CASSANDRA','009',0,'#F5DEB3','rinfusa'),(8,'Blenda BRUNSWICK','008',0,'#F5DEB3','rinfusa'),(7,'Blenda MILPO','007',0,'#F5DEB3','rinfusa'),(6,'Blenda TARA','006',0,'#F5DEB3','rinfusa'),(5,'Blenda COLQUISIRI','005',0,'#F5DEB3','rinfusa'),(4,'Blenda BOUGRINE','004',0,'#F5DEB3','rinfusa'),(3,'Blenda PERUBAR','003',0,'#F5DEB3','rinfusa'),(2,'Blenda UCHUCHACUA','002',0,'#F5DEB3','rinfusa'),(1,'Blenda RED DOG','001',1,'#F5DEB3','rinfusa'),(307,'Blocchi Mattes Zn Alluminate','307',0,'','rinfusa'),(308,'Matte di Zn Galvene','308',0,'','rinfusa'),(309,'Matte di Zn Sollac Desvres','309',0,'','rinfusa'),(310,'Matte di Zn Capitelli','310',0,'','rinfusa'),(311,'Met. di Zn US ZINC sacconi','311',0,'','rinfusa'),(312,'Met. di Zn US ZINC fusti','312',0,'','rinfusa'),(313,'Metalline cuprif. S.Gavino','313',0,'','rinfusa'),(314,'Metalline KSS','314',0,'','rinfusa'),(316,'Anodi di Pb PERTUSOLA','316',0,'','rinfusa'),(317,'Zinco in fusti S.Gavino','317',0,'','rinfusa'),(318,'Residui Piombosi GENCORD','318',0,'','rinfusa'),(319,'Sfridi Zn Elettro','319',0,'','rinfusa'),(320,'Ricicli Elettrolitico','320',0,'','rinfusa'),(321,'Soluzione Zincifera S.Gavino','321',0,'','rinfusa'),(322,'[null]','322',0,'','rinfusa'),(323,'Fanghi tratt. Percol.Genna Luas','323',0,'','rinfusa'),(324,'Fanghi immobiliare FERRERO','324',0,'','rinfusa'),(325,'Fanghi GAMBARI','325',0,'','rinfusa'),(326,'Fanghi NEUTRALIZZAZIONE','326',0,'','rinfusa'),(327,'Fanghi AGGLOMERAZIONE','327',0,'','rinfusa'),(328,'Fanghi CONVERSIONE','328',0,'','rinfusa'),(329,'Mix fanghi AGG/NEU','329',0,'','rinfusa'),(330,'Fanghi da pulizia strade','330',0,'','rinfusa'),(331,'Fanghi GENNA LUAS','331',0,'','rinfusa'),(332,'Fanghi PARAGOETHITE','332',0,'','rinfusa'),(333,'Fanghi Pb/Ag','333',0,'','rinfusa'),(334,'Fanghi TERMOKIMIK','334',0,'','rinfusa'),(335,'Fanghi da conversione pressati','335',0,'','rinfusa'),(336,'Fanghi da conversione 1a produzione','336',0,'','rinfusa'),(337,'Fanghi/Residui di Zn MONTEPONI','337',0,'','rinfusa'),(338,'Fanghi Imp. Tratt. Acque NISI','338',0,'','rinfusa'),(339,'Fanghi di Zinco','339',0,'','rinfusa'),(340,'Fanghi DIEFEMBACH','340',0,'','rinfusa'),(344,'Concentrato zinco Ox Turco','344',0,'','rinfusa'),(345,'PIRITE','345',0,'','rinfusa'),(346,'Pirite GENNA LUAS','346',0,'','rinfusa'),(347,'Zinco ossidato STAKOW','347',0,'','rinfusa'),(348,'MANGANESE','348',0,'','rinfusa'),(349,'Misti di cava','349',0,'','rinfusa'),(350,'Dross da ISF','350',0,'','rinfusa'),(351,'Torbida DYNAWAVE','351',0,'','rinfusa'),(354,'Mix Melme Anodiche','354',0,'','rinfusa'),(355,'Cementi misti COBALTO','355',0,'','rinfusa'),(356,'Granelle schium. pani da Elettrolisi','356',0,'','rinfusa'),(357,'Fini Tuzie da Elettrolisi','357',0,'','rinfusa'),(358,'BRICCHETTE','358',0,'','rinfusa'),(359,'SINTER','359',0,'','rinfusa'),(360,'Fini SINTER','360',0,'','rinfusa'),(361,'Recuperi AGGLOMERAZIONE','361',0,'','rinfusa'),(362,'Recuperi WAELZ','362',0,'','rinfusa'),(363,'Mix recuperi LISCIVIAZIONE','363',0,'','rinfusa'),(364,'Recuperi KIVCET','364',0,'','rinfusa'),(365,'Spurghi da collettore Ovoidale','365',0,'','rinfusa'),(366,'Residui decuprazione Ag','366',0,'','rinfusa'),(367,'Residui decuprazione Pb','367',0,'','rinfusa'),(368,'Residui cupriferi con zolfo','368',0,'','rinfusa'),(369,'Residui di Decuprazione','369',0,'','rinfusa'),(370,'Fini fondo crog./polvox P.NOSSA','370',0,'','rinfusa'),(371,'Semilav.graniglia inerti SIMAR','371',0,'','rinfusa'),(372,'Calamina ANGOURAN','372',0,'','rinfusa'),(373,'Scarti bricchette EC.','373',0,'','rinfusa'),(374,'Costre forno KSS','374',0,'','rinfusa'),(375,'Solfato di piombo','375',0,'','rinfusa'),(376,'Solfato Pb/Ag P.Vesme','376',0,'','rinfusa'),(377,'Cementi purificazione 2°St','377',0,'','rinfusa'),(378,'CLINKER','378',0,'','rinfusa'),(379,'Pulizie blende carica Zn','379',0,'','rinfusa'),(380,'Pulizie ciclo KSS sopravaglio','380',0,'','rinfusa'),(382,'Lastre di piombo','382',0,'','rinfusa'),(383,'Piombo Cuprifero','383',0,'','rinfusa'),(384,'Piombo cuprifero con zolfo','384',0,'','rinfusa'),(385,'Scarti Bricchette','385',0,'','rinfusa'),(386,'Scarti di rame','386',0,'','rinfusa'),(387,'Griglie di Pb RECOBAT','387',0,'','rinfusa'),(388,'Residui Ferrosi Bricchettati','388',0,'','rinfusa'),(400,'Acido FLUOSILICICO','400',0,'','rinfusa'),(401,'Calce Idrata','401',0,'','rinfusa'),(402,'Fiore di Calce','402',0,'','rinfusa'),(403,'Calcare risino S.Antioco','403',0,'','rinfusa'),(404,'Acqua','404',0,'','rinfusa'),(405,'Olio denso BTZ','405',0,'','rinfusa'),(406,'Olio Comb. Fluido','406',0,'','rinfusa'),(407,'Soda Caustica','407',0,'','rinfusa'),(408,'Sabbia','408',0,'','rinfusa'),(409,'GPL','409',0,'','rinfusa'),(410,'Gasolio','410',0,'','rinfusa'),(411,'Ossigeno','411',0,'#F5FE83','rinfusa'),(412,'Bisolfito Sodico soluzione','412',0,'','rinfusa'),(413,'Ipoclorito di Sodio','413',0,'','rinfusa'),(414,'Acido Cloridrico soluzione','414',0,'','rinfusa'),(415,'Cloruro Ferrico','415',0,'','rinfusa'),(416,'Solfuro di Sodio soluzione','416',0,'','rinfusa'),(417,'Sodio Carbonato','417',0,'','rinfusa'),(418,'Acqua Ossigenata','418',0,'','rinfusa'),(419,'Calcare risino Narcao','419',0,'','rinfusa'),(450,'Scorie Imperial Smelting','450',0,'','rinfusa'),(451,'Scorie Waelz','451',0,'','rinfusa'),(452,'Scorie Kivcet','452',0,'','rinfusa'),(453,'Scorie Waelz forno 1','453',0,'','rinfusa'),(454,'Scorie Waelz forno 2','454',0,'','rinfusa'),(456,'Mater. da pulizia Banchina Porto','456',0,'','rinfusa'),(457,'Mater. da pulizia Canaletta','457',0,'','rinfusa'),(458,'Calcestruzzo da demolizioni','458',0,'','rinfusa'),(459,'Assimilati Urbani','459',0,'','rinfusa'),(460,'Acqua da Discarica','460',0,'','rinfusa'),(461,'Pneumatici usati','461',0,'','rinfusa'),(462,'Trasformatori contenenti PCB','462',0,'','rinfusa'),(463,'Mat. costruz. a base di amianto','463',0,'','rinfusa'),(464,'Riv. e refratt. inutilizzabili','464',0,'','rinfusa'),(465,'Residui oleosi vari','465',0,'','rinfusa'),(466,'Accumulatori','466',0,'','rinfusa'),(467,'Residui oleosi e grassi','467',0,'','rinfusa'),(468,'Rifiuti materiali demolizioni inerti','468',0,'','rinfusa'),(469,'Scoticamenti vari','469',0,'','rinfusa'),(470,'Sterili prov. da sbancamento','470',0,'','rinfusa'),(471,'Rifiuti Metalli Ferrosi','471',0,'','rinfusa'),(472,'Terre da Parco Rottami','472',0,'','rinfusa'),(473,'Contenitori metallici','473',0,'','rinfusa'),(474,'Rifiuti metallici contaminati','474',0,'','rinfusa'),(475,'Terra/Rocce con sost. pericolose','475',0,'','rinfusa'),(476,'Materiali Isolanti','476',0,'','rinfusa'),(477,'Lastre di Alluminio','477',0,'','rinfusa'),(478,'Barrotti di Aluminio','478',0,'','rinfusa'),(479,'Rottame di Rame','479',0,'','rinfusa'),(480,'Altre apparecchiat.fuori uso','480',0,'','rinfusa'),(481,'Pesi Campione','481',0,'','rinfusa'),(482,'Peso Campione','482',0,'','rinfusa'),(483,'Residui Minerali','483',0,'','rinfusa'),(484,'Loppe di fusione','484',0,'','rinfusa'),(496,'Piombo d\'opera ISF','496',0,'','rinfusa'),(497,'Acido solforico ARROSTIMENTO','497',0,'','rinfusa'),(498,'Acido solforico AGGLOMERAZIONE','498',0,'','rinfusa'),(499,'Zinco HG I.S.F.','499',0,'','rinfusa'),(500,'Zinco HG ELETTRO','500',0,'','rinfusa'),(501,'Zinco GOB','501',0,'','rinfusa'),(502,'Zinco SHG I.S.F.','502',0,'','rinfusa'),(503,'Zinco SHG ELETTRO','503',0,'','rinfusa'),(504,'Catodi di Zinco','504',0,'','rinfusa'),(505,'Piombo Decuprato KSS','505',0,'','rinfusa'),(506,'Lingottoni','506',0,'','rinfusa'),(507,'Spugne Cadmio','507',0,'','rinfusa'),(508,'Cementi Rame','508',0,'','rinfusa'),(509,'Mercurio','509',0,'','rinfusa'),(510,'Cadmio','510',0,'','rinfusa'),(511,'Piombo di Liquazione','511',0,'','rinfusa'),(512,'Piombo dec. KSS 4 ton.','512',0,'','rinfusa'),(513,'Blocchi Zinco GOB','513',0,'','rinfusa'),(514,'Purga Cadmio','514',0,'','rinfusa'),(515,'Lega Cd/Zn','515',0,'','rinfusa'),(600,'Merce Non Codificata','600',0,'','rinfusa'),(599,'Materiali Vari','599',0,'','rinfusa'),(1376,'Blenda Paragsha','035',NULL,'#F5DEB3',NULL),(1377,'Carbonato di Piombo','153',0,'#F5DEB3','rinfusa');
/*!40000 ALTER TABLE `merci` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registrodoganale`
--

DROP TABLE IF EXISTS `registrodoganale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registrodoganale` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idmerce` int(11) NOT NULL DEFAULT '0',
  `idconsegna` int(11) NOT NULL DEFAULT '0',
  `data` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `idstallo` smallint(5) unsigned DEFAULT '0',
  `isscarico` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `isrettifica` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `secco` double DEFAULT NULL,
  `umido` double NOT NULL DEFAULT '0',
  `numregistro` int(10) unsigned DEFAULT '0',
  `doctype` varchar(10) DEFAULT NULL,
  `docnum` varchar(50) DEFAULT NULL,
  `docdate` datetime DEFAULT NULL,
  `docpvtype` varchar(10) DEFAULT NULL,
  `docpvnum` varchar(50) DEFAULT NULL,
  `docpvdate` datetime DEFAULT NULL,
  `note` tinytext,
  `posdoganale` varchar(255) DEFAULT NULL,
  `locked` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `deleted` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_merce` (`idmerce`),
  KEY `idx_cons` (`idconsegna`)
) ENGINE=MyISAM AUTO_INCREMENT=668 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registrodoganale`
--

LOCK TABLES `registrodoganale` WRITE;
/*!40000 ALTER TABLE `registrodoganale` DISABLE KEYS */;
INSERT INTO `registrodoganale` VALUES (376,1,34,'2010-10-06 00:00:00',22,0,0,8020563,8825600,5212,'IM7','9Q','2010-10-06 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(378,12,36,'2011-06-04 00:00:00',27,0,0,3271856,3590960,5412,'IM7','3K','2011-06-04 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(443,1,34,'2010-12-09 00:00:00',22,1,0,8020563,8825600,5243,'IM4','88M','2010-12-09 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(445,12,36,'2011-07-29 00:00:00',27,1,0,3271856,3590960,5490,'IM4','46X','2011-07-29 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(446,12,39,'2011-07-08 00:00:00',29,0,0,2917460,3206000,5464,'IM7','4V','2011-07-07 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(447,12,39,'2011-07-22 00:00:00',29,0,1,59693,12040,5470,'R','55Z','2011-07-22 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(448,12,40,'2011-08-08 00:00:00',30,0,0,3185000,3500000,5497,'IM7','5X','2011-08-06 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(449,12,40,'2011-08-12 00:00:00',30,0,1,53460,22000,5501,'R',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(635,5,45,'2011-08-29 00:00:00',16,0,0,4109811,4517560,5517,'IM7','6Z','2011-08-17 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(578,120,44,'2011-06-22 00:00:00',4,0,0,118260,131400,5437,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(579,120,44,'2011-06-23 00:00:00',4,0,0,248166,275740,5439,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(580,120,44,'2011-06-24 00:00:00',4,0,0,244386,271540,5440,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(581,120,44,'2011-06-25 00:00:00',4,0,0,227790,253100,5442,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(582,120,44,'2011-06-26 00:00:00',4,0,0,236880,263200,5443,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(583,120,44,'2011-06-27 00:00:00',4,0,0,230976,256640,5444,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(584,120,44,'2011-06-28 00:00:00',4,0,0,184446,204940,5445,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(585,120,44,'2011-06-28 00:00:00',4,1,0,1361934,1513260,5446,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(586,120,44,'2011-06-29 00:00:00',4,0,0,298404,331560,5447,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(587,120,44,'2011-06-30 00:00:00',4,0,0,291276,323640,5449,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(588,120,44,'2011-07-01 00:00:00',4,0,0,269406,299340,5451,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(589,120,44,'2011-07-02 00:00:00',4,0,0,236754,263060,5452,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(590,120,44,'2011-07-03 00:00:00',4,0,0,214974,238860,5453,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(591,120,44,'2011-07-04 00:00:00',4,0,0,131508,146120,5455,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(592,120,44,'2011-07-05 00:00:00',4,0,0,279666,310740,5456,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(593,120,44,'2011-07-06 00:00:00',4,0,0,83556,92840,5460,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(594,120,44,'2011-07-08 00:00:00',4,1,0,1390032,1544480,5465,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(595,120,44,'2011-07-11 00:00:00',4,0,0,163530,181700,5467,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(596,120,44,'2011-07-12 00:00:00',4,0,0,246996,274440,5469,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(597,120,44,'2011-07-13 00:00:00',4,0,0,248436,276040,5471,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(598,120,44,'2011-07-14 00:00:00',4,0,0,237960,264400,5473,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(599,120,44,'2011-07-15 00:00:00',4,0,0,273600,304000,5474,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(600,120,44,'2011-07-16 00:00:00',4,0,0,240084,266760,5475,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(601,120,44,'2011-07-17 00:00:00',4,0,0,258570,287300,5476,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(602,120,44,'2011-07-18 00:00:00',4,0,0,271314,301460,5477,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(603,120,44,'2011-07-19 00:00:00',4,0,0,264384,293760,5478,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(604,120,44,'2011-07-20 00:00:00',4,0,0,147654,164060,5479,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(605,120,44,'2011-07-21 00:00:00',4,0,0,111150,123500,5480,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(606,120,44,'2011-07-22 00:00:00',4,0,0,170874,189860,5481,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(607,120,44,'2011-07-22 00:00:00',4,1,0,1497798,1664220,5482,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(608,120,44,'2011-07-23 00:00:00',4,0,0,283392,314880,5483,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(609,120,44,'2011-07-24 00:00:00',4,0,0,292266,324740,5484,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(610,120,44,'2011-07-25 00:00:00',4,0,0,213462,237180,5485,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(611,120,44,'2011-07-26 00:00:00',4,0,0,185472,206080,5486,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(612,120,44,'2011-07-27 00:00:00',4,0,0,331218,368020,5487,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(613,120,44,'2011-07-28 00:00:00',4,0,0,327924,364360,5488,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(614,120,44,'2011-07-29 00:00:00',4,0,0,107298,119220,5489,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(615,120,44,'2011-08-03 00:00:00',4,1,0,1465128,1627920,5491,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(616,120,44,'2011-08-05 00:00:00',4,0,0,178488,198320,5492,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(617,120,44,'2011-08-06 00:00:00',4,0,0,277254,308060,5494,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(618,120,44,'2011-08-07 00:00:00',4,0,0,276966,307740,5495,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(619,120,44,'2011-08-08 00:00:00',4,0,0,191304,212560,5496,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(620,120,44,'2011-08-09 00:00:00',4,0,0,228996,254440,5498,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(621,120,44,'2011-08-10 00:00:00',4,0,0,301212,334680,5499,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(622,120,44,'2011-08-11 00:00:00',4,0,0,157878,175420,5500,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(623,120,44,'2011-08-12 00:00:00',4,0,0,43416,48240,5502,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(624,120,44,'2011-08-16 00:00:00',4,1,0,1433952,1593280,5504,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(625,120,44,'2011-08-22 00:00:00',4,0,0,97848,108720,5507,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(626,120,44,'2011-08-23 00:00:00',4,0,0,163368,181520,5508,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(627,120,44,'2011-08-24 00:00:00',4,0,0,302022,335580,5510,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(628,120,44,'2011-08-25 00:00:00',4,0,0,259974,288860,5511,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(629,120,44,'2011-08-26 00:00:00',4,0,0,175302,194780,5512,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(630,120,44,'2011-08-26 00:00:00',4,1,0,1401912,1557680,5513,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(631,120,44,'2011-08-27 00:00:00',4,0,0,251244,279160,5514,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(632,120,44,'2011-08-28 00:00:00',4,0,0,272160,302400,5515,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(633,120,44,'2011-08-29 00:00:00',4,0,0,270162,300180,5516,NULL,NULL,NULL,NULL,NULL,NULL,'Errore al stabilire se il movimento è una rettifica: \r\nDestinazione nulla ! (false)',NULL,0,0),(636,5,45,'2011-08-29 00:00:00',1,0,0,4062177,4465200,5517,'IM7','6Z','2011-08-17 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(637,120,44,'2011-08-30 00:00:00',4,0,0,274428,304920,5518,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(638,120,44,'2011-08-31 00:00:00',4,0,0,270828,300920,5521,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(639,120,44,'2011-09-01 00:00:00',4,0,0,29808,33120,5522,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(640,120,44,'2011-09-06 00:00:00',4,1,0,1388574,1542860,5524,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(641,1,46,'2011-08-30 00:00:00',21,0,0,8327611,9201780,5519,'IM7','8N','2011-08-29 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(642,1,46,'2011-09-17 00:00:00',21,0,1,83125,0,5537,'R','56D','2011-09-17 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(643,1,46,'2011-08-30 00:00:00',13,0,0,3872712,4279240,5519,'IM7','8N','2011-08-29 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(644,1,46,'2011-09-17 00:00:00',13,0,1,-118051,-173980,5537,'R','56D','2011-09-17 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(645,1,46,'2011-08-30 00:00:00',10,0,0,3977982,4395560,5519,'IM7','8N','2011-08-29 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(646,1,46,'2011-09-17 00:00:00',10,0,1,40473,0,5537,'R','56D','2011-09-17 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(647,1,46,'2011-08-30 00:00:00',8,0,0,3843028,4246440,5519,'IM7','8N','2011-08-29 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(648,1,46,'2011-09-17 00:00:00',8,0,1,39100,0,5537,'R','56D','2011-09-17 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(649,1,46,'2011-08-30 00:00:00',5,0,0,3881943,4289440,5519,'IM7','8N','2011-08-29 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(650,1,46,'2011-09-17 00:00:00',5,0,1,39496,0,5537,'R','56D','2011-09-17 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(651,1,46,'2011-08-30 00:00:00',2,0,0,1889224,2087540,5519,'IM7','8N','2011-08-29 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(652,1,46,'2011-09-17 00:00:00',2,0,1,19221,0,5537,'R','56D','2011-09-17 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(654,120,48,'2011-09-06 00:00:00',3,0,0,200371,208720,5523,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(653,1,47,'2011-08-30 00:00:00',9,0,0,3891500,4300000,5520,'IM7','7T','2011-08-29 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(655,120,48,'2011-09-07 00:00:00',3,0,0,257952,268700,5525,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(656,120,48,'2011-09-08 00:00:00',3,0,0,229747,239320,5526,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(657,120,48,'2011-09-09 00:00:00',3,0,0,203885,212380,5527,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(658,120,48,'2011-09-10 00:00:00',3,0,0,238675,248620,5528,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(659,120,48,'2011-09-11 00:00:00',3,0,0,158458,165060,5529,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(660,5,45,'2011-09-12 00:00:00',16,1,0,4109811,4517560,5530,'IM4','55Z','2011-09-12 00:00:00',NULL,NULL,NULL,NULL,NULL,0,1),(661,5,45,'2011-09-12 00:00:00',16,1,0,4109811,4517560,5530,'IM4','55Z','2011-09-12 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(662,12,39,'2011-09-13 00:00:00',29,1,0,2977153,3218040,5531,'IM4','56T','2011-09-13 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(663,120,48,'2011-09-12 00:00:00',3,0,0,92064,95900,5532,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(664,120,48,'2011-09-13 00:00:00',3,0,0,147456,153600,5533,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(665,120,48,'2011-09-14 00:00:00',3,0,0,185779,193520,5534,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(666,120,48,'2011-09-15 00:00:00',3,0,0,216941,225980,5535,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(667,120,48,'2011-09-16 00:00:00',3,0,0,244051,254220,5536,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0);
/*!40000 ALTER TABLE `registrodoganale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registroiva`
--

DROP TABLE IF EXISTS `registroiva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registroiva` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idmerce` int(11) NOT NULL DEFAULT '0',
  `idconsegna` int(11) NOT NULL DEFAULT '0',
  `data` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `idstallo` smallint(5) unsigned DEFAULT '0',
  `isscarico` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `isrettifica` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `secco` double DEFAULT NULL,
  `umido` double NOT NULL DEFAULT '0',
  `numregistro` int(10) unsigned DEFAULT '0',
  `doctype` varchar(10) DEFAULT NULL,
  `docnum` varchar(50) DEFAULT NULL,
  `docdate` datetime DEFAULT NULL,
  `docpvtype` varchar(10) DEFAULT NULL,
  `docpvnum` varchar(50) DEFAULT NULL,
  `docpvdate` datetime DEFAULT NULL,
  `note` tinytext,
  `posdoganale` varchar(255) DEFAULT NULL,
  `locked` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `deleted` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `valoreeuro` double NOT NULL DEFAULT '0',
  `valoredollari` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_merce` (`idmerce`),
  KEY `idx_cons` (`idconsegna`)
) ENGINE=MyISAM AUTO_INCREMENT=136 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registroiva`
--

LOCK TABLES `registroiva` WRITE;
/*!40000 ALTER TABLE `registroiva` DISABLE KEYS */;
INSERT INTO `registroiva` VALUES (70,5,35,'2011-05-27 00:00:00',23,0,0,6390258,6977460,5,'IM4','39Z','2011-05-27 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0,0,0),(71,20,37,'2007-01-08 00:00:00',28,0,0,2876750,3099180,1,'IM4','5X','2007-01-08 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0,0,0),(86,20,37,'2011-04-21 00:00:00',28,1,0,64011,68960,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,64011,64011),(87,20,37,'2011-05-26 00:00:00',28,1,0,51313,55280,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,51313,51313),(88,20,37,'2011-06-06 00:00:00',28,1,0,47451,51120,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,47451,47451),(89,20,37,'2011-06-10 00:00:00',28,1,0,28627,30840,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,28627,28627),(90,20,37,'2011-06-15 00:00:00',28,1,0,58126,62620,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,58126,58126),(91,20,37,'2011-06-20 00:00:00',28,1,0,54246,58440,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,54246,54246),(92,20,37,'2011-06-21 00:00:00',28,1,0,353136,380440,10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,353136,353136),(93,20,37,'2011-06-22 00:00:00',28,1,0,549493,591980,11,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,549493,549493),(94,1,34,'2010-12-09 00:00:00',22,0,0,8020563,8825600,2,'IM4','88M','2010-12-09 00:00:00',NULL,NULL,NULL,NULL,'EXT',0,0,8020563,8020563),(95,1,34,'2011-08-19 00:00:00',22,1,0,1223623,1346440,19,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,1223623,1223623),(96,1,34,'2011-08-22 00:00:00',22,1,0,895443,985320,20,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,895443,895443),(97,1,34,'2011-08-25 00:00:00',22,1,0,1074201,1182020,23,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,1074201,1074201),(98,1,34,'2011-09-01 00:00:00',22,1,0,794368,874100,6017,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,1,794368,794368),(99,5,35,'2011-07-01 00:00:00',23,1,0,1326379,1448260,12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,1326379,1326379),(100,5,35,'2011-07-12 00:00:00',23,1,0,986876,1077560,13,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,986876,986876),(101,5,35,'2011-07-19 00:00:00',23,1,0,851276,929500,14,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,851276,851276),(102,5,35,'2011-07-29 00:00:00',23,1,0,508293,555000,16,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,508293,508293),(103,5,35,'2011-08-11 00:00:00',23,1,0,461273,503660,17,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,461273,461273),(104,5,35,'2011-07-29 00:00:00',23,1,0,266419,290900,16,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,266419,266419),(105,5,35,'2011-08-26 00:00:00',23,1,0,850031,928140,24,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,850031,850031),(110,12,36,'2011-07-29 00:00:00',27,0,0,3271856,3590960,15,'IM4','46X','2011-07-29 00:00:00',NULL,NULL,NULL,NULL,'EXT',0,0,3271856,3271856),(111,12,36,'2011-08-18 00:00:00',27,1,0,979071,1074560,18,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,979071,979071),(112,12,36,'2011-08-24 00:00:00',27,1,0,1134019,1244620,21,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,1134019,1134019),(113,20,37,'2011-08-24 00:00:00',28,1,0,476052,512860,22,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,476052,476052),(114,12,36,'2011-08-31 00:00:00',27,1,0,246408,270440,25,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,246408,246408),(115,1,34,'2011-09-01 00:00:00',22,1,0,794368,874100,26,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,794368,794368),(116,1,34,'2011-09-08 00:00:00',22,1,0,807745,888820,31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,807745,807745),(117,1,34,'2011-09-09 00:00:00',22,1,0,529457,582600,32,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,529457,529457),(124,5,35,'2011-09-07 00:00:00',23,1,0,352434,384820,29,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,281054,281054),(118,5,35,'2011-09-02 00:00:00',23,1,0,787277,859620,27,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,787277,787277),(119,5,35,'2011-09-07 00:00:00',23,1,0,281054,306880,29,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,1,281054,281054),(120,5,35,'2011-09-07 00:00:00',23,0,1,50335,54960,30,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,1,50335,50335),(121,12,36,'2011-09-05 00:00:00',27,1,0,794165,871620,28,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,794165,794165),(122,12,36,'2011-09-07 00:00:00',27,1,0,84535,92780,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,1,84535,84535),(123,12,36,'2011-09-07 00:00:00',27,0,1,33657,36940,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,1,33657,33657),(125,5,35,'2011-09-07 00:00:00',23,0,1,50335,54960,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,1,50335,50335),(126,12,36,'2011-09-07 00:00:00',27,1,0,118193,129720,30,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,84535,84535),(127,12,36,'2011-09-07 00:00:00',27,0,1,33657,36940,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,1,33657,33657),(128,5,45,'2011-09-12 00:00:00',16,0,0,4109811,4517560,33,'IM4','55Z','2011-09-12 00:00:00',NULL,NULL,NULL,NULL,'EXT',0,0,2894233.09859155,4109811),(129,12,39,'2011-09-13 00:00:00',29,0,0,2977153,3218040,34,'IM4','56T','2011-09-13 00:00:00',NULL,NULL,NULL,NULL,'EXT',0,0,2096586.61971831,2977153),(130,1,34,'2011-09-14 00:00:00',22,1,0,283868,312360,35,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,283868,283868),(131,1,34,'2011-09-21 00:00:00',22,1,0,612757,674260,36,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,612757,612757),(132,1,34,'2011-09-27 00:00:00',22,1,0,577041,634960,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,577041,577041),(133,20,37,'2011-09-21 00:00:00',28,1,0,704897,759400,37,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,704897,704897),(134,20,37,'2011-09-22 00:00:00',28,0,1,192348,207220,39,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,192348,192348),(135,20,37,'2011-09-22 00:00:00',28,1,0,681747,734460,38,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,681747,681747);
/*!40000 ALTER TABLE `registroiva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stalli`
--

DROP TABLE IF EXISTS `stalli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stalli` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL DEFAULT '',
  `parco` varchar(45) NOT NULL DEFAULT '',
  `numero` tinyint(8) unsigned NOT NULL DEFAULT '0',
  `idconsegnaattuale` int(11) DEFAULT NULL,
  `idconsegnaprenotata` int(11) DEFAULT NULL,
  `caricato` double NOT NULL DEFAULT '0',
  `attuale` double NOT NULL DEFAULT '0',
  `codice` varchar(10) NOT NULL DEFAULT '',
  `liberapratica` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `ix_parco` (`parco`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stalli`
--

LOCK TABLES `stalli` WRITE;
/*!40000 ALTER TABLE `stalli` DISABLE KEYS */;
INSERT INTO `stalli` VALUES (1,'Cap. Parco Nord Stallo 1','NORD',1,45,NULL,0,-4408320,'101',0),(2,'Cap. Parco Nord stallo 2','NORD',2,46,NULL,0,0,'102',0),(3,'Cap. Parco Nord stallo 3','NORD',3,48,NULL,628000,3866620,'103',0),(4,'Cap. Parco Nord Stallo 4','NORD',4,44,NULL,-302800,1950400,'104',0),(5,'Cap. Parco Nord Stallo 5','NORD',5,46,NULL,0,0,'105',0),(6,'Cap. Parco Nord Stallo 6','NORD',6,NULL,NULL,0,0,'106',0),(7,'Cap. Parco Nord Stallo 7','NORD',7,NULL,NULL,0,0,'107',0),(8,'Cap. Parco Nord Stallo 8','NORD',8,46,NULL,0,0,'108',0),(9,'Cap. Parco Nord Stallo 9','NORD',9,47,NULL,0,0,'109',0),(10,'Cap. Parco Nord Stallo 10','NORD',10,46,NULL,0,0,'110',0),(11,'Cap. Parco Nord Stallo 11','NORD',11,NULL,NULL,0,-4231300,'111',0),(12,'Cap. Parco Nord Stallo 12','NORD',12,NULL,NULL,0,0,'112',0),(13,'Cap. Parco Nord Stallo 13','NORD',13,46,NULL,0,0,'113',0),(14,'Cap. Parco Nord Stallo 14','NORD',14,NULL,NULL,0,0,'114',0),(15,'Cap. Parco Nord Stallo 15','NORD',15,NULL,NULL,0,0,'115',0),(16,'Cap. Parco Nord Stallo 16','NORD',16,45,NULL,0,0,'116',1),(21,'Parco est Box 1','EST',1,46,NULL,0,0,'931',0),(22,'Parco est Box 2','EST',2,34,NULL,0,-8354980,'932',1),(23,'Parco est Box 3','EST',3,35,NULL,0,-7096480,'933',0),(24,'Parco est Box 4','EST',4,NULL,NULL,0,0,'904',0),(25,'Parco est Box 5','EST',5,NULL,NULL,0,-1498940,'905',0),(26,'Parco est Box 6','EST',6,NULL,NULL,0,0,'906',0),(27,'Parco est Box 7','EST',7,NULL,NULL,0,-3572920,'907',0),(28,'Parco est Box 8','EST',8,37,NULL,0,-3099180,'908',0),(29,'Parco est Box 9','EST',9,39,NULL,0,0,'909',1),(30,'Parco est Box 10','EST',10,40,NULL,0,0,'910',0),(31,'Parco est Box 11','EST',11,NULL,NULL,0,0,'911',0),(32,'Parco est Box 12','EST',12,NULL,NULL,0,0,'912',0);
/*!40000 ALTER TABLE `stalli` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `Host` char(60) COLLATE utf8_bin NOT NULL DEFAULT '',
  `User` char(16) COLLATE utf8_bin NOT NULL DEFAULT '',
  `Password` char(41) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL DEFAULT '',
  `Select_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Insert_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Update_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Delete_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Drop_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Reload_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Shutdown_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Process_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `File_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Grant_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `References_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Index_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Alter_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Show_db_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Super_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_tmp_table_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Lock_tables_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Execute_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Repl_slave_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Repl_client_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_view_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Show_view_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_routine_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Alter_routine_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_user_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Event_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Trigger_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `ssl_type` enum('','ANY','X509','SPECIFIED') CHARACTER SET utf8 NOT NULL DEFAULT '',
  `ssl_cipher` blob NOT NULL,
  `x509_issuer` blob NOT NULL,
  `x509_subject` blob NOT NULL,
  `max_questions` int(11) unsigned NOT NULL DEFAULT '0',
  `max_updates` int(11) unsigned NOT NULL DEFAULT '0',
  `max_connections` int(11) unsigned NOT NULL DEFAULT '0',
  `max_user_connections` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`Host`,`User`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users and global privileges';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('localhost','root','*CCED19C4214D52F36BE228CF283BF547CEC5A4D0','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0),('%','magazzini','*CCED19C4214D52F36BE228CF283BF547CEC5A4D0','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','','','','',0,0,0,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-10-17 22:00:00
