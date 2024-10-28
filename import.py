from sqlalchemy import create_engine
from sqlalchemy import text,sql

import math
import pymysql
import datetime
import os, sys, fnmatch
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from unicodedata import normalize
from _decimal import Decimal
from pathlib import Path

OOTP_DATABASE = os.environ.get('OOTP_DATABASE')
OOTP_USERNAME = os.environ.get('OOTP_USERNAME')
OOTP_PASSWORD = os.environ.get('OOTP_PASSWORD')

connectionString = "mysql+pymysql://" + OOTP_USERNAME + ":" + OOTP_PASSWORD + "@" + OOTP_DATABASE + "/ootp?ssl_ca=DigiCertGlobalRootCA.crt.pem";
print(f"{connectionString}")

sqlEngine = create_engine(connectionString, pool_recycle=3600)

def load(game,file):
  tableName = 'stats'
  now = str(datetime.datetime.now())
  #print(f'Now:{now}')
  #tables = pd.read_html('file:///mnt/c/Users/Dan/Documents/Out of the Park Developments/OOTP Baseball 22/saved_games/6071a3e8605bf4054e6d8df1.pt/news/html/temp/2021-04-10-12-13-40.html')
  print(f'File: {file}')
  #tables = pd.read_html('file://'+file)  
  tables = pd.read_html(file)
  #print(f'Total tables: {len(tables)}')
  #print(f'{tables}')
  df = pd.DataFrame(data=tables[1])
  rows = len(df.index);
  columns = len(df.columns);  
  print(f'Rows: {rows}  Columns: {columns}')
  #Removed 57 to make sure I use the new format
  # 2 is my collection
  # XX is the card DB

  if columns != 72 and columns != 54 and columns != 60 and columns != 78 and columns != 56 and columns != 2:
      print(f"Columns is not 54, returning");
      return 
  #Common renames
  if columns == 54 or columns == 60 or columns == 57 or columns == 72 and columns != 56:      
      df.rename(columns = {'PI/PA':'pipa'}, inplace = True)  
      df['b_pitches'] = df['pipa'] * df['PA'] 
      df['innings'] = np.trunc(df['IP']) + (np.modf(df['IP'])[0] * (10/3))
      df.rename(columns = {'Unnamed: 0':'date'}, inplace = True)
      #df.rename(columns = {'UnnamedC_0':'date'}, inplace = True)
      df['date'] = df['date'].astype('datetime64[ns]')
      df['date'] = now
      df['game'] = game

  if columns == 72:
        df.rename(columns = {'1B':'singles'}, inplace = True)
        df.rename(columns = {'2B':'doubles'}, inplace = True)
        df.rename(columns = {'3B':'triples'}, inplace = True)
        df.rename(columns = {'G.1':'p_games'}, inplace = True)
        df.rename(columns = {'GS.1':'p_gamesstarted'}, inplace = True)
        df.rename(columns = {'AB.1':'p_ab'}, inplace = True)
        df.rename(columns = {'1B.1':'p_singles'}, inplace = True)
        df.rename(columns = {'2B.1':'p_doubles'}, inplace = True)
        df.rename(columns = {'3B.1':'p_triples'}, inplace = True)
        df.rename(columns = {'HR.1':'p_homeruns'}, inplace = True)
        df.rename(columns = {'R.1':'p_runs'}, inplace = True)
        df.rename(columns = {'BB.1':'p_bb'}, inplace = True)
        df.rename(columns = {'IBB.1':'p_ibb'}, inplace = True)
        df.rename(columns = {'HP.1':'p_hp'}, inplace = True)
        df.rename(columns = {'SB.1':'p_sb'}, inplace = True)
        df.rename(columns = {'CS.1':'p_cs'}, inplace = True)
        df.rename(columns = {'SF.1':'p_sf'}, inplace = True)
        df.rename(columns = {'SH.1':'p_sh'}, inplace = True)
        df.rename(columns = {'ORG':'TM'}, inplace = True)

        if 'LEA' in df.columns:
            df.rename(columns = {'LEA':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'LiveQuick'
        if 'LOY' in df.columns:
            df.rename(columns = {'LOY':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'StandardPerfectDraft'        
        if 'AD' in df.columns:
            df.rename(columns = {'AD':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'Bronze'
        if 'FIN' in df.columns:
            df.rename(columns = {'FIN':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'Gold'
        if 'WE' in df.columns:
            df.rename(columns = {'WE':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'LiveSilver'
        if 'INT' in df.columns:
            df.rename(columns = {'INT':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'LowDiamond'
        if 'Type' in df.columns:
            df.rename(columns = {'Type':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'Silver'
        if 'Txn' in df.columns:
            df.rename(columns = {'Txn':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'Diamond'
        #Tm
        if 'Perf' in df.columns:
            df.rename(columns = {'Perf':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'Open'
        if 'Role' in df.columns:
            df.rename(columns = {'Role':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'LiveGold'
        #Chem        
        if 'Prone' in df.columns:
            df.rename(columns = {'Prone':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'LeaguevR'
        #INJ
        #INJ
        if 'Left' in df.columns:
            df.rename(columns = {'Left':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'LeaguevL'



 

  if columns == 78:
        del df['ID']
        df['DEF'] = df['DEF'].replace(to_replace='-', value = 0)
        df['TDP'] = df['TDP'].replace(to_replace='-', value = 0)
        df['OF RNG'] = df['OF RNG'].replace(to_replace='-', value = 0)
        df['OF ERR'] = df['OF ERR'].replace(to_replace='-', value = 0)
        df['OF ARM'] = df['OF ARM'].replace(to_replace='-', value = 0)
        df.rename(columns = {'K\'s':'ks'}, inplace = True)
        df.rename(columns = {'CON vL':'convl'}, inplace = True)
        df.rename(columns = {'GAP vL':'gapvl'}, inplace = True)
        df.rename(columns = {'POW vL':'powvl'}, inplace = True)
        df.rename(columns = {'EYE vL':'eyevl'}, inplace = True)
        df.rename(columns = {'K vL':'kvl'}, inplace = True)
        df.rename(columns = {'CON vR':'convr'}, inplace = True)
        df.rename(columns = {'GAP vR':'gapvr'}, inplace = True)
        df.rename(columns = {'POW vR':'powvr'}, inplace = True)
        df.rename(columns = {'EYE vR':'eyevr'}, inplace = True)
        df.rename(columns = {'K vR':'kvr'}, inplace = True)
        df.rename(columns = {'CON.1':'control'}, inplace = True)
        df.rename(columns = {'STU vL':'stuffvl'}, inplace = True)
        df.rename(columns = {'MOV vL':'movementvl'}, inplace = True)
        df.rename(columns = {'CON vL.1':'controlvl'}, inplace = True)
        df.rename(columns = {'STU vR':'stuffvr'}, inplace = True)
        df.rename(columns = {'MOV vR':'movementvr'}, inplace = True)
        df.rename(columns = {'CON vR.1':'controlvr'}, inplace = True)
        df.rename(columns = {'C ABI':'cability'}, inplace = True)
        df.rename(columns = {'C ARM':'carm'}, inplace = True)
        df.rename(columns = {'IF RNG':'ifrng'}, inplace = True)
        df.rename(columns = {'IF ERR':'iferr'}, inplace = True)
        df.rename(columns = {'IF ARM':'ifarm'}, inplace = True)
        df.rename(columns = {'OF ARM':'ofarm'}, inplace = True)
        df.rename(columns = {'OF ERR':'oferr'}, inplace = True)
        df.rename(columns = {'OF RNG':'ofrng'}, inplace = True)
        df.rename(columns = {'G/F':'gf'}, inplace = True)

      
  dbConnection    = sqlEngine.connect()
  try:    
    tableName = 'stats' + str(columns)
    if columns == 77:
        frame = df.to_sql(tableName, dbConnection, if_exists='append',index=False);
    elif columns == 2:
        frame = df.to_sql(tableName, dbConnection, if_exists='replace', schema="ootp");           
    else :   
        frame = df.to_sql(tableName, dbConnection, if_exists='append', schema="ootp");           
  except ValueError as vx:
    print(f'vx:{vx}')
  except Exception as ex:   
    print(f'ex:{ex}')
  else:
    dbConnection.commit()
    print("Table %s updates."%tableName);   
  finally:
    dbConnection.close()

#load('file:///mnt/c/Users/Dan/Documents/Out of the Park Developments/OOTP Baseball 22/saved_games/6071a3e8605bf4054e6d8df1.pt/news/html/temp/2021-04-10-12-13-40.html')
#fileFrame.to_sql('skip', dbConnection, if_exists='append');

# Open a file
#path = "c:/Users/dan/Documents/Out of the Park Developments/OOTP Baseball 24/saved_games"
path = "c:\\Users\\Dan\\Documents\\Out of the Park Developments\\OOTP Baseball 25\\saved_games"
#path = "C:/OOTP/saved_games"
pattern = "[0-9]*.html"

#dirs = os.listdir( path )

# This would print all the files and directories
#for file in dirs:
#       print(file)
print(Path.cwd())

print(f'path: {path}')
result = []
for root, dirs, files in os.walk(path):
  for name in files:
    if fnmatch.fnmatch(name, pattern):
      fullPath = os.path.join(Path(root).as_posix(), name)
      #print(f'FP: {fullPath}')
      #fullPath = "c:\\Users\\Dan\\Documents\\Out of the Park Developments\\OOTP Baseball 24\\saved_games\\7ea1000000000000000000c3.pt\\news\\html\\temp\\2023-03-24-21-16-59.html"
      with sqlEngine.connect() as connection:  
          query =  "select file from skip where file = '" + fullPath + "'"       
          result = connection.execute(text(query))
          game = fullPath[85:109]
          if result.rowcount > 0:
              print(f'Found skipping {fullPath}')
          else :
              #print('Not found')
              query = sql.text("insert into skip (file) values ('" + fullPath + "')")
              connection.execute(query)
              connection.commit()
              load(game,fullPath)

# cardDBFile = "C:/OOTP/online_data/pt_card_list.csv"
cardDBFile = "c:\\Users\\dan\\Documents\\Out of the Park Developments\\OOTP Baseball 25\\online_data\\pt_card_list.csv"
if os.path.isfile(cardDBFile):
      print("File exists")
      cards = pd.read_csv(cardDBFile, index_col=False)
      df = pd.DataFrame(data=cards)
      df.columns = df.columns.str.replace(' ', '')
      df.rename(columns = {'//CardTitle':'CardTitle'}, inplace = True)
      df.rename(columns = {'Unnamed:111':'Unnamed111'}, inplace = True)
      df.rename(columns = {'InfieldRange':'ifr'}, inplace = True)
      df.rename(columns = {'FirstName':'fn'}, inplace = True)
      df.rename(columns = {'LastName':'ln'}, inplace = True)
      df.rename(columns = {'PosRatingC':'ratingC'}, inplace = True)
      df.rename(columns = {'PosRating1B':'rating1B'}, inplace = True)
      df.rename(columns = {'PosRating2B':'rating2B'}, inplace = True)
      df.rename(columns = {'PosRating3B':'rating3B'}, inplace = True)
      df.rename(columns = {'PosRatingSS':'ratingSS'}, inplace = True)
      df.rename(columns = {'PosRatingLF':'ratingLF'}, inplace = True)
      df.rename(columns = {'PosRatingCF':'ratingCF'}, inplace = True)
      df.rename(columns = {'PosRatingRF':'ratingRF'}, inplace = True)
      df.rename(columns = {'AvoidKvL':'KsvL'}, inplace = True)
      df.rename(columns = {'CardValue':'Overall'}, inplace = True)
      #df.rename(columns = {'':'Blank'}, inplace = True)
      df['date']= pd.to_datetime(df['date'])

      rows = len(df.index)
      columns = len(df.columns) 
      #df.index = df['CardID']
      #print(df.index)
      #df.index = df.index.astype('int64')
      #df.rename_axis('index')
      #print('Rows: '+ str(rows) + ' Column: ' + str(columns))
      # iterating the columns
      #for col in df.columns:
        #print("A" + col)
      dbConnection = sqlEngine.connect()
      try:
        # need to rename cards to Cards, why?    
        # rename table cards to Cards;
        tableName = "cards"        
        frame = df.to_sql(tableName, dbConnection, if_exists='replace', schema="ootp");           
      except ValueError as vx:
         print(f'vx:{vx}')
      except Exception as ex:   
         print(f'ex:{ex}')
      else:
         dbConnection.commit()
         print("Card DB created successfully.");   
      finally:
         dbConnection.close()

ptLiveHitterDownload = "C:\\Users\\dan\\Downloads\\FantasyPros_2024_Projections_H.csv"
ptliveHitterFile = "C:\\Users\\dan\\Code\\ootp-stats\\src\\main\\resources\\FantasyPros_2024_Projections_H.csv"
if os.path.isfile(ptLiveHitterDownload):
    print("Found new hitter download")
    if os.path.isfile(ptliveHitterFile):
            print("Old Hitter File exists")
            os.remove(ptliveHitterFile)
    os.rename(ptLiveHitterDownload, ptliveHitterFile)
    
ptLivePitcherDownload = "C:\\Users\\dan\\Downloads\\FantasyPros_2024_Projections_P.csv"
ptlivePitcherFile = "C:\\Users\\dan\\Code\\ootp-stats\\src\\main\\resources\\FantasyPros_2024_Projections_P.csv"
if os.path.isfile(ptLivePitcherDownload):
    print("Found new pitcher download")
    if os.path.isfile(ptlivePitcherFile):
            print("Old Pitcher File exists")
            os.remove(ptlivePitcherFile)
    os.rename(ptLivePitcherDownload, ptlivePitcherFile)
    
