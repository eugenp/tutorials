FROM php:8.0-apache as base

# INSTALL PHP EXTENSIONS
RUN docker-php-ext-install pdo_mysql
# ANABLE APACHE MOD REWRITE
RUN a2enmod rewrite
# ANABLE APACHE MOD HEADER
RUN a2enmod headers
# UPDATE APT-GET AND INSTALL LIBS
RUN apt-get update -y
RUN apt-get install -y openssl zip unzip git libnss3 libpng-dev
# INSTALL NODE
#RUN curl -sL https://deb.nodesource.com/setup_14.x | bash -
#RUN apt-get install -y nodejs build-essential
#RUN ln -s /usr/local/bin/node /usr/local/bin/nodejs
# INSTALL COMPOSER
#RUN apt-get install -y openssl zip unzip git libnss3
#RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer
# Change www-data user to match the host system UID and GID and chown www directory
RUN usermod --non-unique --uid 1000 www-data \
  && groupmod --non-unique --gid 1000 www-data \
  && chown -R www-data:www-data /var/www
# Defines that the image will have port 80 to expose
EXPOSE 80
WORKDIR /var/www/html
