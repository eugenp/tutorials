import { Injectable } from '@angular/core';

/**
 * An utility service for link parsing.
 */
@Injectable({
  providedIn: 'root',
})
export class ParseLinks {
  /**
   * Method to parse the links
   */
  parseAll(header: string): { [key: string]: { [key: string]: string | undefined } | undefined } {
    if (header.length === 0) {
      throw new Error('input must not be of zero length');
    }

    // Split parts by comma
    const parts: string[] = header.split(',');

    // Parse each part into a named link
    return Object.fromEntries(
      parts.map(p => {
        const section: string[] = p.split(';');

        if (section.length !== 2) {
          throw new Error('section could not be split on ";"');
        }

        const url: string = section[0].replace(/<(.*)>/, '$1').trim(); // NOSONAR
        const queryString: { [key: string]: string } = {};

        url.replace(/([^?=&]+)(=([^&]*))?/g, (_$0: string, $1: string | undefined, _$2: string | undefined, $3: string | undefined) => {
          if ($1 !== undefined && $3 !== undefined) {
            queryString[$1] = decodeURIComponent($3);
          }
          return $3 ?? '';
        });

        const name: string = section[1].replace(/rel="(.*)"/, '$1').trim();
        return [name, queryString];
      }),
    );
  }

  /**
   * Method to parse the links
   */
  parse(header: string): { [key: string]: number } {
    const sections = this.parseAll(header);
    const links: { [key: string]: number } = {};
    for (const [name, queryParams] of Object.entries(sections)) {
      if (queryParams?.page !== undefined) {
        links[name] = parseInt(queryParams.page, 10);
      }
    }
    return links;
  }
}
