/* NOTE: To make rows hide, adjust the CSS by uncommenting line 24. */
$(document).on('keyup', '#search-input', function() {
	var rows = $('#searchable-table tr').not('.avoid'),
	caseInsensitive = ($('#caseinsensitive').is(":checked")), //Set to false if you would like for the searches to respect case sensitivity.
	query = parse_query((caseInsensitive === true ? $(this).val().toLowerCase():$(this).val())),
	results = filter_rows(rows, query, caseInsensitive);
	$('#results').empty().append(results+' record(s) found!');
});

$(document).on('keyup', '#search-pitchers', function() {
	var rows = $('#searchable-pitchers tr').not('.avoid'),
	caseInsensitive = ($('#caseinsensitive').is(":checked")), //Set to false if you would like for the searches to respect case sensitivity.
	query = parse_query((caseInsensitive === true ? $(this).val().toLowerCase():$(this).val())),
	results = filter_rows(rows, query, caseInsensitive);
	$('#results').empty().append(results+' record(s) found!');
});

$(document).on('change', '#caseinsensitive', function() {
	$('#search-input').trigger('keyup');
});

//Filters rows based on search conditions (Highlights Red - See: CSS row 21)
function filter_rows(rows, query, caseInsensitive) {
	var r = 0;

	if(rows.length > 0) { //Check if there are any rows at all.
		$.each(rows, function(e, k) { //Iterate rows.
			var td = $(this).children('td'), //Get child cells of current row.
			span = td.children('span'),
			name = (caseInsensitive === true ? span[0].textContent.toLowerCase():span[0].textContent), //Get name in 2nd cell of current row.
			dept = (caseInsensitive === true ? td[0].innerText.toLowerCase():td[0].innerText); //Get dept in 3rd cell of current row.
			if(!jQuery.isEmptyObject(query.or)) { //Check if any OR values are available in the array.
				if(isMatching(name, query.or) || isMatching(dept, query.or)) { //If name or dept is found matching in the 'OR' values, show row.
					if($(this).hasClass('hide')) { $(this).removeClass('hide'); }
				} else {
					if(!jQuery.isEmptyObject(query.and)) {
						if(isMatching(name, query.and) && isMatching(dept, query.and)) {
							if($(this).hasClass('hide')) { $(this).removeClass('hide'); }
						} else {
							if(!$(this).hasClass('hide')) { $(this).addClass('hide'); }
						}
					} else {
						if(!$(this).hasClass('hide')) { $(this).addClass('hide'); }
					}
				}
			} else {
				if(!jQuery.isEmptyObject(query.and)) {
					if(isMatching(name, query.and) && isMatching(dept, query.and)) {
						if($(this).hasClass('hide')) { $(this).removeClass('hide'); }
					} else {
						if(!$(this).hasClass('hide')) { $(this).addClass('hide'); }
					}
				} else {
					if(!$(this).hasClass('hide')) { $(this).addClass('hide'); }
				}
			}
		});
	}
	return r;
}


//Takes string and converts it to an object of arrays containing distinct AND + OR values seperately.
function parse_query(str) {
	var or = str.split(","),
	defaultOr = true, //If true, single value searches default as an 'OR' operator value.
	params = {
	'or':[],
	'and':[]
	};
	if((or.length - 1) > 0 || (str.split("+").length - 1) > 0) { //If any 'AND' or 'OR' parameters found in string.
		$.each(or, function(k,v) {
			var and = v.split("+");
			//If value contains any AND operators
			if((and.length - 1) > 0) {
				console.log('%o',and);
				if(and[0].trim().length > 0) { //If the first value is not empty, add it to the 'OR' operator value list.
					params.and.push(and[0].trim());
				}
				and.splice(0,1); //Remove 'OR' value from array
				$.each(and, function(ka,va) { //Add additional 'AND' values if they exist in string.
					if(va.trim().length > 0) {
						params.and.push(va.trim());
					}
				});
			} else {
				if(v.trim().length > 0) {
					params.or.push(v.trim());
				}
			}
		});
	} else { //If no delimeters found, default value to an operator (Default: 'OR').
		if(str.trim().length > 0) { //If string is not empty.
			if(defaultOr) {
				params.or[0] = str.trim();
			} else {
				params.and[0] = str.trim();
			}
		}
	}
	populate_filter_preview(params);
	return params;
}


//Populates Filter row with an example of the search that is filtering the rows.
function populate_filter_preview(params) {
	var p = $('#filter-preview');
	p.empty();
	if(params.or.length > 0 || params.and.length > 0) {
		if(params.or.length > 0) {
			$.each(params.or, function(k,v) {
				p.append('<span class="or">'+v+'</span>');
			});
		}
		if(params.and.length > 0) {
			$.each(params.and, function(k,v) {
				p.append('<span class="and">'+v+'</span>');
			});
		}
	} else {
		p.empty();
	}
}

//Simple short function to return boolean if string found in array.
function isMatching(s,a) {
   var found = false;
   $.each(a, function(i, val) {
     console.log( i + ":" + val + ":" + s + ":" + s.indexOf(val) );
     if (s.indexOf(val) >= 0) {
        found = true;
      }
    })
	return found;
}
