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

sqlEngine = create_engine("mysql+pymysql://ootp:ootp2222@server/ootp", pool_recycle=3600)

def load(game,file):
  tableName = 'stats'
  now = str(datetime.datetime.now())
  print(f'Now:{now}')
  #tables = pd.read_html('file:///mnt/c/Users/Dan/Documents/Out of the Park Developments/OOTP Baseball 22/saved_games/6071a3e8605bf4054e6d8df1.pt/news/html/temp/2021-04-10-12-13-40.html')
  print(f'File: {file}')
  #tables = pd.read_html('file://'+file)  
  tables = pd.read_html(file)
  print(f'Total tables: {len(tables)}')
  #print(f'{tables}')
  df = pd.DataFrame(data=tables[1])
  rows = len(df.index);
  columns = len(df.columns);  
  print(f'Rows: {rows}  Columns: {columns}')
  if columns != 54 and columns != 60 and columns != 57 and columns != 78 and columns != 56 and columns != 2:
      print(f"Columns is not 54, returning");
      return 
  #Common renames
  if columns == 54 or columns == 60 or columns == 57 and columns != 56:      
      df.rename(columns = {'PI/PA':'pipa'}, inplace = True)  
      df['b_pitches'] = df['pipa'] * df['PA'] 
      df['innings'] = np.trunc(df['IP']) + (np.modf(df['IP'])[0] * (10/3))
      df.rename(columns = {'Unnamed: 0':'date'}, inplace = True)
      #df.rename(columns = {'UnnamedC_0':'date'}, inplace = True)
      df['date'] = df['date'].astype('datetime64[ns]')
      df['date'] = now
      df['game'] = game
  
  if columns == 57:
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
        df.rename(columns = {'ORG':'TM'}, inplace = True)

        if 'LEA' in df.columns:
            df.rename(columns = {'LEA':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'PerfectDraft'
        if 'LOY' in df.columns:
            df.rename(columns = {'LOY':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'PerfectTeam'        
        if 'AD' in df.columns:
            df.rename(columns = {'AD':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'Bronze16'
        if 'GRE' in df.columns:
            df.rename(columns = {'GRE':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'Gold32'
        if 'WE' in df.columns:
            df.rename(columns = {'WE':'tournament_type'}, inplace = True)
            df['tournament_type'] = 'Iron16'

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
    print("Table %s created successfully."%tableName);   
  finally:
    dbConnection.close()

#load('file:///mnt/c/Users/Dan/Documents/Out of the Park Developments/OOTP Baseball 22/saved_games/6071a3e8605bf4054e6d8df1.pt/news/html/temp/2021-04-10-12-13-40.html')
#fileFrame.to_sql('skip', dbConnection, if_exists='append');

# Open a file
path = "c:/Users/Dan/Documents/Out of the Park Developments/OOTP Baseball 24/saved_games"
pattern = "[0-9]*.html"

#dirs = os.listdir( path )

# This would print all the files and directories
#for file in dirs:
#       print(file)

result = []
for root, dirs, files in os.walk(path):
  for name in files:
    if fnmatch.fnmatch(name, pattern):
      fullPath = os.path.join(Path(root).as_posix(), name)
      print(f'FP: {fullPath}')
      #fullPath = "c:\\Users\\Dan\\Documents\\Out of the Park Developments\\OOTP Baseball 24\\saved_games\\7ea1000000000000000000c3.pt\\news\\html\\temp\\2023-03-24-21-16-59.html"
      with sqlEngine.connect() as connection:  
          query =  "select file from skip where file = '" + fullPath + "'"       
          result = connection.execute(text(query))
          game = fullPath[85:109]
          if result.rowcount > 0:
              print(f'Found skipping {game}')
          else :
              print('Not found')
              query = sql.text("insert into skip (file) values ('" + fullPath + "')")
              connection.execute(query)
              connection.commit()
              load(game,fullPath)

cardDBFile = "C:/Users/dan/Documents/Out of the Park Developments/OOTP Baseball 24/online_data/pt_card_list.csv"

if os.path.isfile(cardDBFile):
      print("File exists")
      cards = pd.read_csv(cardDBFile)
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

      rows = len(df.index)
      columns = len(df.columns) 
      print('Rows: '+ str(rows) + ' Column: ' + str(columns))
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

#print(skipFrame)
#for file in result:
#           print(file)

#print(result);
