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
  `valoreunitario` double unsigned DEFAULT '1',
  PRIMARY KEY (`idConsegna`)
) ENGINE=MyISAM AUTO_INCREMENT=60 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consegne`
--

LOCK TABLES `consegne` WRITE;
/*!40000 ALTER TABLE `consegne` DISABLE KEYS */;
INSERT INTO `consegne` VALUES (45,275,5,1,'2011-09-20 10:33:29','2011-12-19 15:12:26',8982760,1,'Callao','Perù','Chollada Naree','7100','26080000','EXT',1.42,9.02587,763,0,1),(46,277,1,1,'2011-09-20 10:44:32',NULL,28500000,0,'Kivalina','USA','Torm Trader','7100','26080000','EXT',1.42,8.579236,765,0,1),(49,317,12,1,'2011-11-15 10:41:38',NULL,4538000,0,'Safi','Marocco','m/v Anzoras','7100','26080000','EXT',1,8.446364,768,0,1),(50,341,1,1,'2011-11-15 10:45:27',NULL,29011396,0,'Kivalina','USA','m/v Mishima','7100','26080000','EXT',1,8.721793,770,0,NULL),(51,358,9,4,'2011-10-31 00:00:00','2011-12-20 14:54:28',2934840,0,'xxx','Grecia','m/v Jason','altro','26080000','COM',1,7.4164,772,0,NULL),(52,342,1,2,'2011-11-17 10:00:29','2011-12-19 15:15:41',4395604,0,'KIVALINA','USA','m/v Mishima','4500','26080000','XNZ',1.362418624,8.393316,771,0,0.86459),(54,0,120,4,'2011-11-14 00:00:00','2012-01-05 09:34:49',0,0,'Portovesme','Italia','camion','altro','altro','NAZ',1,4,773,0,0),(58,0,120,4,'2011-11-02 00:00:00',NULL,NULL,0,'Portovesme','Italia','camion','altro','altro','NAZ',1,4,775,0,NULL),(56,395,12,1,'2011-12-09 03:06:48',NULL,5720000,0,'-','-','Brens','7100','26080000','EXT',1,9.974467,774,1,0),(57,412,95,2,'2011-12-09 11:15:37','2011-12-20 15:05:30',9670451,1,'Nikolaev','Ukraina','m/v  Nazli Deniz','4500','altro','XNZ',1.3387,0,776,0,0.183),(59,0,120,4,'2011-12-28 00:00:00',NULL,NULL,0,'Portovesme','Italia','camion','altro','altro','NAZ',1,4,777,0,1);
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
INSERT INTO `iter` VALUES (1,'ExtraComunitaria','Si considera il Peso della Bolla Doganale, Immissione in Reg.Doganale con IM7, passaggo a R.Iva con IM4',0,1,1,1,1,0,'select  min( data )  as mindata , sum(netto)  as PesoNetto,  merce, cliente, fornitore, destinazione\r\n from `archivio corretto` where \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  %ANDS%\r\ngroup by  cliente,  merce, `num consegna` , `num documento`, fornitore, destinazione\r\norder by  min( data )','\r\nselect data   , sum( peso ) as PesoNetto, merce ,  \'%S%\' as fornitore  ,  0 as destinazione\r\nFROM (\r\n\r\nselect   data   , sum( IIF(fornitore = \'0\'  , - netto , netto)) as peso ,  merce\r\n from `archivio corretto` where \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  and\r\n(  fornitore = \'%S%\' or ( cliente =  \'%S%\'  and destinazione = 8 ) )\r\nand data =  #%D%#\r\ngroup by  data , fornitore ,  merce, `num consegna` , `num documento`\r\n\r\n) p\r\n\r\ngroup by data, merce'),(2,'EC Nazionalizzata','Si considera il Peso della Bolla Doganale, Immissione direttamente in Reg.Iva con IM4',0,1,0,1,1,0,'select  min( data )  as mindata , sum(netto)  as PesoNetto,  merce, cliente, fornitore, destinazione\r\n from `archivio corretto` where \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  %ANDS%\r\ngroup by  cliente,  merce, `num consegna` , `num documento`, fornitore, destinazione\r\norder by  min( data )','\r\nselect data   , sum( peso ) as PesoNetto, merce ,  \'%S%\' as fornitore  ,  0 as destinazione\r\nFROM (\r\n\r\nselect   data   , sum( IIF(fornitore = \'0\'  , - netto , netto)) as peso ,  merce\r\n from `archivio corretto` where \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  and\r\n(  fornitore = \'%S%\' or ( cliente =  \'%S%\'  and destinazione = 8 ) )\r\nand data =  #%D%#\r\ngroup by  data , fornitore ,  merce, `num consegna` , `num documento`\r\n\r\n) p\r\n\r\ngroup by data, merce'),(3,'Comunitaria Glencore','Si considera il Peso della Fattura, Immissione direttamente in Reg.Iva senza IM4',1,0,0,1,1,0,'select  min( data )  as mindata , sum(netto)  as PesoNetto,  merce, cliente, fornitore, destinazione\r\n from `archivio corretto` where \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  %ANDS%\r\ngroup by  cliente,  merce, `num consegna` , `num documento`, fornitore, destinazione\r\norder by  min( data )','\r\nselect data   , sum( peso ) as PesoNetto, merce ,  \'%S%\' as fornitore  ,  0 as destinazione\r\nFROM (\r\n\r\nselect   data   , sum( IIF(fornitore = \'0\'  , - netto , netto)) as peso ,  merce\r\n from `archivio corretto` where \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  and\r\n(  fornitore = \'%S%\' or ( cliente =  \'%S%\'  and destinazione = 8 ) )\r\nand data =  #%D%#\r\ngroup by  data , fornitore ,  merce, `num consegna` , `num documento`\r\n\r\n) p\r\n\r\ngroup by data, merce'),(4,'Comunitaria NON Glencore','Entra in Reg.Doganale con i singoli pesi, non passa in R.IVA',0,0,1,0,0,1,'select sum(netto) as PesoNetto, data,  merce,  `num consegna` , \'%S%\' as cliente, 0 as destinazione\r\n from `archivio corretto` where \r\ndata = #%D%# and\r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  and \r\ncliente  = \'%S%\' and destinazione <> 8 \r\ngroup by data, merce, `num consegna`','\r\nselect data   , sum( peso ) as PesoNetto, merce ,  \'%S%\' as fornitore  ,  0 as destinazione\r\nFROM (\r\n\r\nselect   data   , sum( IIF(fornitore = \'0\'  , - netto , netto)) as peso ,  merce\r\n from `archivio corretto` where \r\nmerce = \'%M%\' and `num consegna` = \'%C%\'  and\r\n(  fornitore = \'%S%\' or ( cliente =  \'%S%\'  and destinazione = 8 ) )\r\nand data =  #%D%#\r\ngroup by  data , fornitore ,  merce, `num consegna` , `num documento`\r\n\r\n) p\r\n\r\ngroup by data, merce');
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
) ENGINE=MyISAM AUTO_INCREMENT=832 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registrodoganale`
--

LOCK TABLES `registrodoganale` WRITE;
/*!40000 ALTER TABLE `registrodoganale` DISABLE KEYS */;
INSERT INTO `registrodoganale` VALUES (758,120,54,'2011-11-20 00:00:00',3,0,0,188909,196780,5579,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(757,120,54,'2011-11-19 00:00:00',3,0,0,190214,198140,5578,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(756,120,54,'2011-11-18 00:00:00',3,0,0,237485,247380,5577,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(755,120,54,'2011-11-17 00:00:00',3,0,0,244128,254300,5576,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(635,5,45,'2011-08-29 00:00:00',16,0,0,4109811,4517560,5517,'IM7','6Z','2011-08-17 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(636,5,45,'2011-08-29 00:00:00',1,0,0,4062177,4465200,5517,'IM7','6Z','2011-08-17 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(686,1,46,'2011-08-30 00:00:00',21,0,0,8170159,9027800,5537,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(660,5,45,'2011-09-12 00:00:00',16,1,0,4109811,4517560,5530,'IM4','55Z','2011-09-12 00:00:00',NULL,NULL,NULL,NULL,NULL,0,1),(661,5,45,'2011-09-12 00:00:00',16,1,0,4109811,4517560,5530,'IM4','55Z','2011-09-12 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(687,1,46,'2011-09-17 00:00:00',21,0,1,83125,0,5538,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(688,1,46,'2011-08-30 00:00:00',13,0,0,4030164,4453220,5537,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(689,1,46,'2011-09-17 00:00:00',13,0,1,-118051,-173980,5538,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(690,1,46,'2011-08-30 00:00:00',10,0,0,3977982,4395560,5537,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(691,1,46,'2011-09-17 00:00:00',10,0,1,40473,0,5538,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(692,1,46,'2011-08-30 00:00:00',8,0,0,3843028,4246440,5537,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(693,1,46,'2011-09-17 00:00:00',8,0,1,39100,0,5538,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(694,1,46,'2011-08-30 00:00:00',5,0,0,3881943,4289440,5537,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(695,1,46,'2011-09-17 00:00:00',5,0,1,39496,0,5538,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(696,1,46,'2011-08-30 00:00:00',2,0,0,1889224,2087540,5537,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(697,1,46,'2011-09-17 00:00:00',2,0,1,19222,0,5538,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(718,12,49,'2011-10-03 00:00:00',27,0,0,4129580,4538000,5559,'IM7','9Q','2011-10-03 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(788,1,50,'2011-10-05 00:00:00',6,0,0,3928579,4317120,5598,'IM7','10X','2011-11-05 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(787,1,50,'2011-10-05 00:00:00',7,0,0,4006603,4402860,5598,'IM7','10X','2011-11-05 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(786,1,50,'2011-10-05 00:00:00',11,0,0,3964324,4356400,5598,'IM7','10X','2011-11-05 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(785,1,50,'2011-10-05 00:00:00',14,0,0,4354201,4784836,5598,'IM7','10X','2011-11-05 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(784,1,50,'2011-10-05 00:00:00',31,0,0,1888359,2075120,5598,'IM7','10X','2011-11-05 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(783,1,50,'2011-10-05 00:00:00',23,0,0,8258305,9075060,5598,'IM7','10X','2011-11-05 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(727,12,49,'2011-10-10 00:00:00',27,0,1,25124,0,5563,'R','71Z','2011-10-11 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(753,120,54,'2011-11-15 00:00:00',3,0,0,240902,250940,5574,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(735,9,51,'2011-10-31 00:00:00',15,0,0,2015415,2176860,5568,'COM','174','2011-10-27 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(794,1,50,'2011-10-31 00:00:00',6,0,1,12010,0,5599,'R','72T','2011-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(792,1,50,'2011-10-31 00:00:00',11,0,1,12120,0,5599,'R','72T','2011-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(791,1,50,'2011-10-31 00:00:00',14,0,1,-321146,-366416,5599,'R','72T','2011-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(790,1,50,'2011-10-31 00:00:00',31,0,1,5773,0,5599,'R','72T','2011-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(743,9,51,'2011-11-02 00:00:00',15,0,0,701765,757980,5569,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(754,120,54,'2011-11-16 00:00:00',3,0,0,276154,287660,5575,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(749,5,45,'2011-11-08 00:00:00',1,1,0,4062177,4465200,5570,'IM4','68U','2011-11-08 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(752,120,54,'2011-11-14 00:00:00',3,0,0,133037,138580,5573,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(759,120,54,'2011-11-21 00:00:00',3,0,0,297408,309800,5580,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(760,120,54,'2011-11-22 00:00:00',3,0,0,188736,196600,5581,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(761,120,54,'2011-11-23 00:00:00',3,0,0,243264,253400,5582,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(762,120,54,'2011-11-24 00:00:00',3,0,0,189984,197900,5583,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(763,120,54,'2011-11-25 00:00:00',3,0,0,215712,224700,5584,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(764,120,54,'2011-11-26 00:00:00',3,0,0,279840,291500,5585,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(765,120,54,'2011-11-27 00:00:00',3,0,0,292186,304360,5586,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(766,120,54,'2011-11-28 00:00:00',3,0,0,230438,240040,5587,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(767,120,54,'2011-11-29 00:00:00',3,0,0,214522,223460,5588,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(768,120,54,'2011-11-30 00:00:00',3,0,0,282931,294720,5589,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(769,120,54,'2011-11-30 00:00:00',3,1,0,1249766,1301840,5590,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(770,120,54,'2011-12-01 00:00:00',3,0,0,138240,144000,5591,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(771,120,54,'2011-12-02 00:00:00',3,0,0,138240,144000,5592,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(789,1,50,'2011-10-31 00:00:00',23,0,1,25247,0,5599,'R','72T','2011-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(793,1,50,'2011-10-31 00:00:00',7,0,1,12249,0,5599,'R','72T','2011-10-31 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(795,12,56,'2011-11-30 00:00:00',29,0,0,4114729,4521680,5615,'IM7','11Q','2011-11-30 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(796,12,56,'2011-11-30 00:00:00',25,0,0,1090471,1198320,5615,'IM7','11Q','2011-11-30 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(797,1,46,'2011-11-30 00:00:00',21,1,0,8253284,9027800,5600,'IM4','73V','2011-11-30 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(798,12,49,'2011-11-30 00:00:00',27,1,0,4154704,4538000,5601,'IM4','74F','2011-11-30 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(799,120,58,'2011-11-08 00:00:00',4,1,0,418195,435620,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,1),(800,120,58,'2011-11-21 00:00:00',4,1,0,1645210,1713760,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,1),(801,120,58,'2011-11-30 00:00:00',4,0,0,44870,46740,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,1),(802,120,58,'2011-11-30 00:00:00',4,1,0,386534,402640,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,1),(803,120,58,'2011-12-02 00:00:00',4,0,0,169152,176200,5602,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(804,120,58,'2011-12-03 00:00:00',4,0,0,268416,279600,5603,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(805,120,58,'2011-12-04 00:00:00',4,0,0,290669,302780,5604,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(806,120,58,'2011-12-05 00:00:00',4,0,0,293530,305760,5605,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(807,120,58,'2011-12-06 00:00:00',4,0,0,180077,187580,5606,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(808,120,58,'2011-12-07 00:00:00',4,0,0,240326,250340,5607,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(809,120,58,'2011-12-08 00:00:00',4,0,0,286003,297920,5608,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(810,120,58,'2011-12-09 00:00:00',4,0,0,292723,304920,5609,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(811,120,58,'2011-12-10 00:00:00',4,0,0,299290,311760,5610,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(812,120,58,'2011-12-11 00:00:00',4,0,0,291245,303380,5611,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(813,120,58,'2011-12-12 00:00:00',4,0,0,266938,278060,5612,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(814,120,58,'2011-12-13 00:00:00',4,0,0,258816,269600,5613,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(815,9,51,'2011-12-13 00:00:00',15,1,0,1249286,1349360,5614,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(816,12,56,'2011-12-13 00:00:00',29,0,1,3561,52900,5616,'R','89P','0011-12-16 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(817,12,56,'2011-12-13 00:00:00',25,0,1,-11677,0,5616,'R','89P','0011-12-16 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(818,120,58,'2011-12-14 00:00:00',4,0,0,261427,272320,5617,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(819,120,58,'2011-12-15 00:00:00',4,0,0,57715,60120,5618,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(820,120,54,'2011-12-15 00:00:00',3,1,0,1692000,1762500,5619,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(824,9,51,'2011-12-16 00:00:00',15,1,0,1467894,1585480,5620,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(825,1,46,'2011-12-19 00:00:00',13,1,0,3912113,4279240,5621,'IM4','77D','2011-12-19 00:00:00',NULL,NULL,NULL,NULL,NULL,0,0),(826,120,54,'2011-12-27 00:00:00',3,1,0,1280563,1333920,5622,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(827,120,58,'2011-12-27 00:00:00',4,1,0,283238,295040,5623,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(828,120,59,'2011-12-28 00:00:00',3,0,0,99610,103760,5624,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(829,120,59,'2011-12-29 00:00:00',3,0,0,256339,267020,5625,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(830,120,59,'2011-12-30 00:00:00',3,0,0,276326,287840,5626,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(831,120,59,'2011-12-31 00:00:00',3,0,0,275078,286540,5627,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0);
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
) ENGINE=MyISAM AUTO_INCREMENT=274 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registroiva`
--

LOCK TABLES `registroiva` WRITE;
/*!40000 ALTER TABLE `registroiva` DISABLE KEYS */;
INSERT INTO `registroiva` VALUES (258,95,57,'2011-12-06 00:00:00',22,0,0,9670451,9670451,80,'IM4','75H','2011-12-05 00:00:00',NULL,NULL,NULL,'introduzione in deposito iva','XNZ',0,0,1321949,1769693),(257,12,49,'2011-11-30 00:00:00',27,0,0,4154704,4538000,79,'IM4','74F','2011-11-30 00:00:00',NULL,NULL,NULL,NULL,'EXT',0,0,4154704,4154704),(256,1,46,'2011-11-30 00:00:00',21,0,0,8253284,9027800,78,'IM4','73V','2011-11-30 00:00:00',NULL,NULL,NULL,NULL,'EXT',0,0,5812171.83098592,8253284),(128,5,45,'2011-09-12 00:00:00',16,0,0,4109811,4517560,33,'IM4','55Z','2011-09-12 00:00:00',NULL,NULL,NULL,NULL,'EXT',0,0,2894233.09859155,4109811),(254,1,52,'2011-12-01 00:00:00',12,1,0,986293,1076660,76,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,625900.916097577,852739.06487),(253,1,52,'2011-11-25 00:00:00',12,1,0,1390553,1517960,75,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,882444.057275306,1202258.21827),(252,1,52,'2011-11-24 00:00:00',12,1,0,1047431,1143400,74,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,664699.052359695,905598.36829),(242,5,45,'2011-12-02 00:00:00',1,1,0,1238740,1361640,71,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,872352.112676056,1238740),(241,5,45,'2011-11-18 00:00:00',1,1,0,810998,891460,70,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,571125.352112676,810998),(239,5,45,'2011-11-08 00:00:00',1,0,0,4062177,4465200,68,'IM4','68U','2011-11-08 00:00:00',NULL,NULL,NULL,NULL,'EXT',0,0,2860688.02816901,4062177),(250,1,52,'2011-10-05 00:00:00',12,0,0,4000000,4395604,72,'IM4',NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,2538397.47862989,3458360),(149,5,45,'2011-10-05 00:00:00',16,1,0,623428,685280,45,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,439033.802816901,623428),(240,5,45,'2011-11-10 00:00:00',1,1,0,941910,1035360,69,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,663316.901408451,941910),(238,5,45,'2011-10-31 00:00:00',16,1,0,485893,534100,67,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,309607.042253521,439642),(237,5,45,'2011-10-26 00:00:00',16,1,0,630705,693280,66,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,444158.450704225,630705),(236,5,45,'2011-10-20 00:00:00',16,1,0,427051,469420,65,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,300740.14084507,427051),(235,5,45,'2011-10-19 00:00:00',16,1,0,325742,358060,64,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,229395.774647887,325742),(233,5,45,'2011-10-10 00:00:00',16,1,0,1300129,1429120,62,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,915583.802816902,1300129),(234,5,45,'2011-10-14 00:00:00',16,1,0,316863,348300,63,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,223142.957746479,316863),(251,1,52,'2011-10-31 00:00:00',12,0,1,19628,-7684,73,'R','73V','2011-10-31 00:00:00',NULL,NULL,NULL,NULL,'XNZ',0,0,12455.9164276369,16970.17252),(259,95,57,'2011-12-06 00:00:00',22,1,0,3757360,3757360,81,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,513630.298050347,687596.88),(260,95,57,'2011-12-07 00:00:00',22,1,0,2553220,2553220,82,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,349024.620900874,467239.26),(261,95,57,'2011-12-09 00:00:00',22,1,0,3308860,3308860,83,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,452320.445208038,605521.38),(263,1,52,'2011-12-09 00:00:00',12,1,0,595351,649900,84,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,406970,554463),(264,95,57,'2011-12-10 00:00:00',22,1,0,51011,51011,85,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'XNZ',0,0,10288.0256965713,13772.58),(267,5,45,'2011-12-14 00:00:00',1,1,0,1070529,1176740,86,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,753893.661971831,1070529),(268,12,49,'2011-12-19 00:00:00',27,1,0,1371364,1497880,87,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,1371364,1371364),(269,1,46,'2011-12-19 00:00:00',13,0,0,3912113,4279240,88,'IM4','77D','2011-12-19 00:00:00',NULL,NULL,NULL,NULL,'EXT',0,0,2755009.15492958,3912113),(270,12,49,'2011-12-20 00:00:00',27,1,0,673615,735760,89,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,673615,673615),(271,1,46,'2011-12-21 00:00:00',13,1,0,1359171,1486720,90,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,957162.676056338,1359171),(272,1,46,'2011-12-22 00:00:00',13,1,0,1021408,1117260,91,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,719301.408450704,1021408),(273,12,49,'2011-12-29 00:00:00',27,1,0,989127,1080380,92,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EXT',0,0,989127,989127);
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
INSERT INTO `stalli` VALUES (1,'Cap. Parco Nord Stallo 1','NORD',1,NULL,NULL,0,-5703840,'101',0),(2,'Cap. Parco Nord stallo 2','NORD',2,46,NULL,0,0,'102',0),(3,'Cap. Parco Nord stallo 3','NORD',3,59,NULL,286540,945160,'103',0),(4,'Cap. Parco Nord Stallo 4','NORD',4,58,NULL,797960,3303860,'104',0),(5,'Cap. Parco Nord Stallo 5','NORD',5,46,NULL,4419860,4419860,'105',0),(6,'Cap. Parco Nord Stallo 6','NORD',6,50,NULL,4317120,4317120,'106',0),(7,'Cap. Parco Nord Stallo 7','NORD',7,50,NULL,4402860,4402860,'107',0),(8,'Cap. Parco Nord Stallo 8','NORD',8,46,NULL,0,0,'108',0),(9,'Cap. Parco Nord Stallo 9','NORD',9,NULL,NULL,4536800,4536800,'109',0),(10,'Cap. Parco Nord Stallo 10','NORD',10,46,NULL,0,0,'110',0),(11,'Cap. Parco Nord Stallo 11','NORD',11,50,NULL,4356400,4356400,'111',0),(12,'Cap. Parco Nord Stallo 12','NORD',12,NULL,NULL,4387920,-50160,'112',0),(13,'Cap. Parco Nord Stallo 13','NORD',13,46,NULL,0,-2603980,'113',1),(14,'Cap. Parco Nord Stallo 14','NORD',14,50,NULL,4418420,4418420,'114',0),(15,'Cap. Parco Nord Stallo 15','NORD',15,NULL,NULL,2176860,-3128940,'115',0),(16,'Cap. Parco Nord Stallo 16','NORD',16,NULL,NULL,0,0,'116',0),(21,'Parco est Box 1','EST',1,46,NULL,0,0,'931',1),(22,'Parco est Box 2','EST',2,NULL,NULL,9694700,0,'932',0),(23,'Parco est Box 3','EST',3,50,NULL,9075060,9075060,'933',0),(24,'Parco est Box 4','EST',4,NULL,NULL,0,0,'904',0),(25,'Parco est Box 5','EST',5,56,NULL,1198320,1198320,'905',0),(26,'Parco est Box 6','EST',6,NULL,NULL,0,0,'906',0),(27,'Parco est Box 7','EST',7,49,NULL,4538000,1223980,'907',1),(28,'Parco est Box 8','EST',8,NULL,NULL,0,-3099180,'908',0),(29,'Parco est Box 9','EST',9,56,NULL,4574580,4574580,'909',0),(30,'Parco est Box 10','EST',10,NULL,NULL,0,-5483040,'910',0),(31,'Parco est Box 11','EST',11,50,NULL,2075120,2075120,'911',0),(32,'Parco est Box 12','EST',12,NULL,NULL,0,0,'912',0);
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

-- Dump completed on 2012-01-06 16:00:00
