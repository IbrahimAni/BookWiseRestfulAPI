 <!-- Header -->
  <header class="bg-blue-500 p-4 w-full sticky top-0 z-50">
    <div class="container mx-auto flex justify-between items-center">
      <h1 class="text-white font-bold text-xl">BookWise</h1>
      <nav class="hidden md:flex">
        <button onclick="sendRequest()" class="mx-2 text-white">Show All Books</button>
        <button onclick="showAddForm()" class="mx-2 text-white">Add Book</button>
      </nav>
      <div class="flex items-center">
        <input type="text" id="searchParameter" placeholder="Search for books" class="w-64 px-3 py-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-300">
        <button onclick="findBook()" class="bg-blue-300 ml-2 px-4 py-2 rounded text-white font-semibold">Send</button>
      </div>
    </div>
  </header>